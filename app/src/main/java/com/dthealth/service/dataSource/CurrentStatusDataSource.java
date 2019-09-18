package com.dthealth.service.dataSource;

import android.annotation.SuppressLint;

import com.dthealth.service.callback.BaseInterface;
import com.dthealth.service.callback.MessageResultInterface;
import com.dthealth.service.callback.PhysicalIndexResultInterface;
import com.dthealth.service.callback.SocketResultInterface;
import com.dthealth.service.model.MessageContent;
import com.dthealth.service.model.Result;
import com.dthealth.util.StompClient;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class CurrentStatusDataSource {
    private static Gson gson = new Gson();
    private static volatile CompositeDisposable compositeDisposable;
    private Disposable disposablePhysicalIndex;
    private Disposable disposableMessage;

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

    public void socketConnect(SocketResultInterface socketResultInterface) {
        this.connect(socketResultInterface);
    }

    public void physicalIndexSubscribe(BaseInterface baseInterface) {
        subscribe(true, baseInterface);
    }

    public void messageSubscribe(BaseInterface baseInterface) {
        subscribe(false, baseInterface);
    }

    public void physicalIndexUnsubscribe() {
        compositeDisposable.remove(disposablePhysicalIndex);
    }

    public void messageUnSubscribe() {
        compositeDisposable.remove(disposableMessage);
    }

    public void sendMessageToPlatform(Object message, SocketResultInterface socketResultInterface) {
        sendMessage("/queue/toPlatform", null, message, socketResultInterface);
    }

    public void sendMessageToUser(String targetUserId, Object message, SocketResultInterface socketResultInterface) {
        sendMessage("/queue/toUser", targetUserId, message, socketResultInterface);
    }

    private void connect(SocketResultInterface socketResultInterface) {
        if (!StompClient.getInstance().isConnected()) {
            compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(initLifecycle(socketResultInterface));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    StompClient.getInstance().connect();
                    StompClient.getInstance().withClientHeartbeat(10000).withServerHeartbeat(10000);
                }
            }).start();
        }
    }

    private Disposable initLifecycle(SocketResultInterface socketResultInterface) {
        return StompClient.getInstance().lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Consumer<LifecycleEvent>() {
                    @Override
                    public void accept(LifecycleEvent lifecycleEvent) throws Exception {
                        switch (lifecycleEvent.getType()) {
                            case OPENED:
                                socketResultInterface.processResult(new Result.Success<>("Stomp connection opened"));
                                break;
                            case ERROR:
                                socketResultInterface.processResult(new Result.Error(lifecycleEvent.getException()));
                                break;
                            case CLOSED:
                                if (compositeDisposable != null) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable = new CompositeDisposable();
                                socketResultInterface.processResult(new Result.Success<>("Stomp connection closed"));
                                break;
                            case FAILED_SERVER_HEARTBEAT:
                                socketResultInterface.processResult(new Result.Failed<>("Stomp failed server heartbeat"));
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        socketResultInterface.processResult(new Result.Error(new IOException()));
                    }
                });
    }

    private void subscribe(boolean isPhysicalIndex, BaseInterface baseInterface) {
        //create a flowable data which is from remote servers,
        // and the pressure strategy is Latest which means once the data can not be processed in time by downstream ends,
        // the latest data can be kept.
        Flowable<StompMessage> flowable;
        if (isPhysicalIndex) {
            flowable = StompClient.getInstance().topic("/user/topic/physicalIndex").onBackpressureLatest();
            disposablePhysicalIndex = getDisposable(flowable, baseInterface);
            compositeDisposable.add(disposablePhysicalIndex);
        } else {
            flowable = StompClient.getInstance().topic("/user/topic/message").onBackpressureLatest();
            disposableMessage = getDisposable(flowable, baseInterface);
            compositeDisposable.add(disposableMessage);
        }
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
                            if (baseInterface instanceof MessageResultInterface) {
                                ((MessageResultInterface) baseInterface).processMessage(topicMessage.getPayload());
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

    @SuppressLint("CheckResult")
    private void sendMessage(String destination, String targetUser, Object object, SocketResultInterface socketResultInterface) {
        String content;
        if (targetUser != null) {
            content = gson.toJson(new MessageContent(targetUser, object));
        } else {
            content = gson.toJson(new MessageContent("", object));
        }
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

    public static CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
