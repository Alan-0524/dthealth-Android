package com.dthealth.model;

import java.util.List;

public class DetectResult {
    private String id;
    private String detectedTime;
    private List<DetectResultItem> list;

    public DetectResult(String id, String detectedTime, List<DetectResultItem> list) {
        this.id = id;
        this.detectedTime = detectedTime;
        this.list = list;
    }

    public String getContent() {
        return "Your physical condition has " + list.size() + " abnormal signals at " + detectedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetectedTime() {
        return detectedTime;
    }

    public void setDetectedTime(String detectedTime) {
        this.detectedTime = detectedTime;
    }

    public List<DetectResultItem> getList() {
        return list;
    }

    public void setList(List<DetectResultItem> list) {
        this.list = list;
    }
}
