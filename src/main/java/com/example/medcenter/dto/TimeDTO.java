package com.example.medcenter.dto;

import java.util.Date;

public class TimeDTO {
    int order;
    String time;
    boolean isFree;
    int status;
    Date date;
    String dateStr;


    public TimeDTO(){}

    public TimeDTO(int order, String time, String dateStr) {
        this.order = order;
        this.time = time;
        this.dateStr = dateStr;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
