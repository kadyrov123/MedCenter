package com.example.medcenter.dto;

import com.example.medcenter.entity.QueueEntity;

public class QueuePatientsDTO {
    String name;
    String surname;
    QueueEntity queueEntity;
    Boolean canBeCanceled;

    public Boolean getCanBeCanceled() {
        return canBeCanceled;
    }

    public void setCanBeCanceled(Boolean canBeCanceled) {
        this.canBeCanceled = canBeCanceled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public QueueEntity getQueueEntity() {
        return queueEntity;
    }

    public void setQueueEntity(QueueEntity queueEntity) {
        this.queueEntity = queueEntity;
    }
}
