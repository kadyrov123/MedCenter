package com.example.medcenter.repoitory;

import com.example.medcenter.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface QueueRepository extends JpaRepository<QueueEntity, Long> {
    List<QueueEntity> findQueueEntitiesByDateAndDoctorId(Date date , long doctorId);
}
