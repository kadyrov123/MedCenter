package com.example.medcenter.dto;

import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;

public class DoctorsPatientDTO {
    int id;
    UsersEntity patient;
    QueueEntity queue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersEntity getPatient() {
        return patient;
    }

    public void setPatient(UsersEntity patient) {
        this.patient = patient;
    }

    public QueueEntity getQueue() {
        return queue;
    }

    public void setQueue(QueueEntity queue) {
        this.queue = queue;
    }

}
