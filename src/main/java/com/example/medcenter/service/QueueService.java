package com.example.medcenter.service;

import com.example.medcenter.dto.QueueDTO;
import com.example.medcenter.entity.QueueEntity;

public interface QueueService {
    QueueDTO getQueueByDoctorId(long id);
    QueueDTO getQueueByUserId(long id);

    void addToQueue(QueueDTO queueDTO);
    void leaveQueue(long userId);

}
