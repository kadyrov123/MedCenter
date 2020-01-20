package com.example.medcenter.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimetableDTO {
    int id;
    long doctorId;
    List<TimeDTO> timeList;
    Date date;
    String dateString;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public List<TimeDTO> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<TimeDTO> timeList) {
        this.timeList = timeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.dateString = dateFormat.format(date);
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
