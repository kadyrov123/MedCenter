package com.example.medcenter.dto;

import java.sql.Time;
import java.time.LocalTime;

public class PatientVisitsDTO {
    int id;
    String date;
    String startTime;
    String doctorType;
    String doctorFullname;
    int doctorId;
    boolean canBeCanceled;
    Long queueId;

    public PatientVisitsDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorFullname() {
        return doctorFullname;
    }

    public void setDoctorFullname(String doctorFullname) {
        this.doctorFullname = doctorFullname;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isCanBeCanceled() {
        return canBeCanceled;
    }

    public void setCanBeCanceled(boolean canBeCanceled) {
        this.canBeCanceled = canBeCanceled;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }
}
