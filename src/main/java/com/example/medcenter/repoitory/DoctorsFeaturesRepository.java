package com.example.medcenter.repoitory;

import com.example.medcenter.entity.DoctorsFeaturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorsFeaturesRepository extends JpaRepository<DoctorsFeaturesEntity,Integer> {
    DoctorsFeaturesEntity getDoctorsFeaturesEntityByDoctorId(long id);
}
