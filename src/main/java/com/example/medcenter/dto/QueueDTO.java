package com.example.medcenter.dto;

import java.sql.Time;
import java.util.List;

public class QueueDTO {
    int doctorId;
    Time startTime;
    Time endTime;
    int interval;
    List<String> timeToChoose;

    public QueueDTO() {
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<String> getTimeToChoose() {
        return timeToChoose;
    }

    public void setTimeToChoose(List<String> timeToChoose) {
        this.timeToChoose = timeToChoose;
    }
}
