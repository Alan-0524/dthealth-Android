package com.dthealth.dataSource;

import com.dthealth.callback.SocketResultInterface;
import com.dthealth.model.Result;
import com.dthealth.util.CompositeDisposableUtil;
import com.dthealth.util.StompClient;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.dto.LifecycleEvent;

public class StompConnection {

    public void socketConnect(SocketResultInterface socketResultInterface) {
        this.connect(socketResultInterface);
    }

    private void connect(SocketResultInterface socketResultInterface) {
        if (!StompClient.getInstance().isConnected()) {
            CompositeDisposableUtil.getCompositeDisposable().add(initLifecycle(socketResultInterface));
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
                                if (CompositeDisposableUtil.getCompositeDisposable() != null) {
                                    CompositeDisposableUtil.getCompositeDisposable().dispose();
                                }
                                CompositeDisposableUtil.renewCompositeDisposable();
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
}
