package com.dthealth.repository;

import com.dthealth.callback.MessageResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dao.entity.Message;
import com.dthealth.dataSource.MessageDataSource;

public class MessageRepository {
    private static volatile MessageRepository instance;
    private MessageDataSource messageDataSource;

    public MessageRepository(MessageDataSource messageDataSource) {
        this.messageDataSource = messageDataSource;
    }

    public static MessageRepository getInstance(MessageDataSource messageDataSource) {
        if (instance == null) {
            instance = new MessageRepository(messageDataSource);
        }
        return instance;
    }

    public void messageSubscribe(MessageResultInterface messageResultInterface) {
        messageDataSource.messageSubscribe(messageResultInterface);
    }

    public void sendMessageToPlatform(Message message, SocketResultInterface socketResultInterface) {
        messageDataSource.sendMessageToPlatform(message, socketResultInterface);
    }

    public void sendMessageToUser(Message message, SocketResultInterface socketResultInterface) {
        messageDataSource.sendMessageToUser(message, socketResultInterface);
    }
}
