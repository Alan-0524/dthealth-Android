package com.dthealth.service.model;

public class DetectResultItem {
    private String index;
    private String value;
    private String minValue;
    private String maxValue;

    public DetectResultItem(String index, String value, String minValue, String maxValue) {
        this.index = index;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String toString(){
        return this.index+" "+this.value;
    }
}
