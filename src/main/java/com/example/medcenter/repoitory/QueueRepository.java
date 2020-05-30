package com.example.medcenter.repoitory;

import com.example.medcenter.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {
    List<QueueEntity> findQueueEntitiesByDateAndDoctorId(Date date , int doctorId);

    List<QueueEntity> findQueueEntitiesByUserId(long userId);

    List<QueueEntity> findQueueEntitiesByDate(Date date);

    List<QueueEntity> findQueueEntitiesByStatus(int status);

    List<QueueEntity> findQueueEntitiesByDoctorId(int doctorId);

    List<QueueEntity> findAllByDateLessThanEqual(Date date);
    List<QueueEntity> findQueueEntitiesByDateBetween(Date start, Date end);
    List<QueueEntity> findQueueEntitiesByDateAfterAndDoctorId(Date date, int doctorId);

//    @Query(value = "SELECT * FROM queue WHERE date LIKE '%2020-05%'", nativeQuery = true)
    @Query("select q from QueueEntity q where q.date <= : startDate")
    List<QueueEntity> findQueueEntitiesByMonth(@Param("startDate") Date startDate);
//    List<QueueEntity> findQueueEntitiesByMonth(@Param("month") String month);

//    @Query(value = "SELECT * FROM events WHERE event_date Like %?1%", nativeQuery = true)
//    List<Match> findByMatchMonthAndMatchDay(@Param("eventDate") String eventDate);
}
