package com.example.medcenter.repoitory;

import com.example.medcenter.entity.IntervalEntity;
import com.example.medcenter.entity.NewComersEntity;
import com.example.medcenter.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewComersRepository extends JpaRepository<NewComersEntity,Long> {
    NewComersEntity findNewComersEntityByQueue(QueueEntity queue);
}
