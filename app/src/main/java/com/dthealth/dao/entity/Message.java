package com.dthealth.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity
public class Message {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "my_id")
    @SerializedName("myId")
    private String myId;

    @ColumnInfo(name = "object_id")
    @SerializedName("objectId")
    private String objectId;

    @ColumnInfo(name = "create_time")
    @SerializedName("createTime")
    private String createTime;

    @ColumnInfo(name = "state") // 0:unread 1:read
    @SerializedName("state")
    private char state;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private char type; // 0: send 1:receive

    @ColumnInfo(name = "content")
    @SerializedName("content")
    private String content;

    public Message(@NonNull String id, String myId, String objectId, String createTime, char state, char type, String content) {
        this.id = id;
        this.myId = myId;
        this.objectId = objectId;
        this.createTime = createTime;
        this.state = state;
        this.type = type;
        this.content = content;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
