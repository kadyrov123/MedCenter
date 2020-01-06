package com.example.medcenter.dto;

import java.util.List;

public class TimeDTO {
    int order;
    String time;
    boolean isFree;

    public TimeDTO(){}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
