package com.dthealth.repository;

import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dataSource.StompConnection;

public class StompConnectionRepository {
    private static volatile StompConnectionRepository instance;
    private StompConnection socketConnect;

    public StompConnectionRepository(StompConnection socketConnect) {
        this.socketConnect = socketConnect;
    }

    public static synchronized StompConnectionRepository getInstance(StompConnection socketConnect) {
        if (instance == null) {
            instance = new StompConnectionRepository(socketConnect);
        }
        return instance;
    }

    public void socketConnect(SocketResultInterface socketResultInterface) {
        socketConnect.socketConnect(socketResultInterface);
    }
}
