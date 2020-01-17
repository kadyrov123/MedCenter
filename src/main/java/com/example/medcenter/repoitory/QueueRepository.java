package com.example.medcenter.repoitory;

import com.example.medcenter.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {
    List<QueueEntity> findQueueEntitiesByDateAndDoctorId(Date date , long doctorId);
}
