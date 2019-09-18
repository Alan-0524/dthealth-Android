package com.dthealth.service.repository;


import com.dthealth.service.callback.MessageResultInterface;
import com.dthealth.service.callback.PhysicalIndexResultInterface;
import com.dthealth.service.callback.SocketResultInterface;
import com.dthealth.service.dataSource.CurrentStatusDataSource;


public class CurrentStatusRepository {

    private static volatile CurrentStatusRepository instance;
    private CurrentStatusDataSource currentStatusDataSource;

    public CurrentStatusRepository(CurrentStatusDataSource currentStatusDataSource) {
        this.currentStatusDataSource = currentStatusDataSource;
    }

    public static CurrentStatusRepository getInstance(CurrentStatusDataSource currentStatusDataSource) {
        if (instance == null) {
            instance = new CurrentStatusRepository(currentStatusDataSource);
        }
        return instance;
    }

    public void socketConnect(SocketResultInterface socketResultInterface) {
        currentStatusDataSource.socketConnect(socketResultInterface);
    }

    public void physicalIndexSubscribe(PhysicalIndexResultInterface physicalIndexResultInterface) {
        currentStatusDataSource.physicalIndexSubscribe(physicalIndexResultInterface);
    }

    public void messageSubscribe(MessageResultInterface messageResultInterface) {
        currentStatusDataSource.messageSubscribe(messageResultInterface);
    }

    public void sendMessageToPlatform(Object message, SocketResultInterface socketResultInterface) {
        currentStatusDataSource.sendMessageToPlatform(message, socketResultInterface);
    }

    public void sendMessageToUser(String targetUserId, Object message, SocketResultInterface socketResultInterface) {
        currentStatusDataSource.sendMessageToUser(targetUserId, message, socketResultInterface);
    }

}
