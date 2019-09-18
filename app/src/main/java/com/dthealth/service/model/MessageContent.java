package com.dthealth.service.model;

public class MessageContent {
    private String targetUser;
    private Object message;

    public MessageContent(String targetUser, Object message) {
        this.targetUser = targetUser;
        this.message = message;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
