package com.dthealth.model;

import com.google.gson.annotations.SerializedName;

public class PhysicalIndex {


    @SerializedName("bloodPressure")
    private float bloodPressure; //60-100
    @SerializedName("heartbeatStrength")
    private float heartbeatStrength;
    @SerializedName("bloodFat")
    private float bloodFat;
    @SerializedName("bloodGlucose")
    private float bloodGlucose;
    @SerializedName("temperature")
    private float temperature;

    private float heartbeat;

    public PhysicalIndex() {
    }

    public PhysicalIndex(float bloodPressure, float heartbeatStrength, float bloodFat, float bloodGlucose, float temperature) {

        this.bloodPressure = bloodPressure;
        this.heartbeatStrength = heartbeatStrength;
        this.bloodFat = bloodFat;
        this.bloodGlucose = bloodGlucose;
        this.temperature = temperature;
    }


    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(float bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public float getHeartbeatStrength() {
        return heartbeatStrength;
    }

    public void setHeartbeatStrength(float heartbeatStrength) {
        this.heartbeatStrength = heartbeatStrength;
    }

    public float getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(float bloodFat) {
        this.bloodFat = bloodFat;
    }

    public float getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(float bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(float heartbeat) {
        this.heartbeat = heartbeat;
    }
}
