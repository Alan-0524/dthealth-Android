package com.dthealth.dataSource;

import com.dthealth.callback.BaseInterface;
import com.dthealth.callback.PhysicalIndexResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.model.Result;
import com.dthealth.util.CompositeDisposableUtil;
import com.dthealth.util.StompClient;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class CurrentStatusDataSource {

    private Disposable disposablePhysicalIndex;


    public void physicalIndexSubscribe(BaseInterface baseInterface) {
        subscribe(baseInterface);
    }

    public void physicalIndexUnsubscribe() {
        CompositeDisposableUtil.getCompositeDisposable().remove(disposablePhysicalIndex);
    }

    private void subscribe(BaseInterface baseInterface) {
        //create a flowable data which is from remote servers,
        // and the pressure strategy is Latest which means once the data can not be processed in time by downstream ends,
        // the latest data can be kept.
        Flowable<StompMessage> flowable;
        flowable = StompClient.getInstance().topic("/user/topic/physicalIndex").onBackpressureLatest();
        disposablePhysicalIndex = getDisposable(flowable, baseInterface);
        CompositeDisposableUtil.getCompositeDisposable().add(disposablePhysicalIndex);

    }

    private Disposable getDisposable(Flowable<StompMessage> flowable, BaseInterface baseInterface) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<StompMessage>() {
                    @Override
                    public void accept(StompMessage topicMessage) throws Exception {
                        if (topicMessage != null) {
                            if (baseInterface instanceof PhysicalIndexResultInterface) {
                                ((PhysicalIndexResultInterface) baseInterface).assemblePhysicalIndex(topicMessage.getPayload());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        baseInterface.processException(throwable);
                    }
                });
    }


}
