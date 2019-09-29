package com.dthealth.dataSource;

import android.annotation.SuppressLint;

import com.dthealth.callback.BaseInterface;
import com.dthealth.callback.MessageResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dao.entity.Message;
import com.dthealth.model.Result;
import com.dthealth.util.CompositeDisposableUtil;
import com.dthealth.util.StompClient;
import com.google.gson.Gson;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.dto.StompMessage;

public class MessageDataSource {
    private Disposable disposableMessage;
    private Gson gson = new Gson();

    private static CompletableTransformer applySchedulers() {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable upstream) {
                return upstream
                        .unsubscribeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public void sendMessageToPlatform(Message message, SocketResultInterface socketResultInterface) {
        sendMessage("/queue/toPlatform", message, socketResultInterface);
    }

    public void sendMessageToUser(Message message, SocketResultInterface socketResultInterface) {
        sendMessage("/queue/toUser", message, socketResultInterface);
    }

    @SuppressLint("CheckResult")
    private void sendMessage(String destination, Message message, SocketResultInterface socketResultInterface) {
        String content = gson.toJson(message);
        StompClient.getInstance().send(destination, content).compose(applySchedulers()).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                socketResultInterface.processResult(new Result.Success<>("STOMP echo send successfully"));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                socketResultInterface.processResult(new Result.Failed<>(throwable.getMessage()));
            }
        });
    }

    public void messageSubscribe(BaseInterface baseInterface) {
        subscribe(baseInterface);
    }

    public void messageUnSubscribe() {
        CompositeDisposableUtil.getCompositeDisposable().remove(disposableMessage);
    }

    private void subscribe(BaseInterface baseInterface) {
        //create a flowable data which is from remote servers,
        // and the pressure strategy is Latest which means once the data can not be processed in time by downstream ends,
        // the latest data can be kept.
        Flowable<StompMessage> flowable;
        flowable = StompClient.getInstance().topic("/user/topic/message").onBackpressureLatest();
        disposableMessage = getDisposable(flowable, baseInterface);
        CompositeDisposableUtil.getCompositeDisposable().add(disposableMessage);
    }

    private Disposable getDisposable(Flowable<StompMessage> flowable, BaseInterface baseInterface) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<StompMessage>() {
                    @Override
                    public void accept(StompMessage topicMessage) throws Exception {
                        if (topicMessage != null) {
                            if (baseInterface instanceof MessageResultInterface) {
                                Message message = gson.fromJson(topicMessage.getPayload(), Message.class);
                                ((MessageResultInterface) baseInterface).processMessage(message);
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
