package com.dthealth.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBodyInRows<T> {
    @SerializedName("rows")
    List<T> rows;
    @SerializedName("total")
    int total;

    public ResponseBodyInRows() {
    }

    public ResponseBodyInRows(List<T> rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
