package com.example.medcenter.repoitory;

import com.example.medcenter.entity.DoctorsFeaturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsFeaturesRepository extends JpaRepository<DoctorsFeaturesEntity,Integer> {
    DoctorsFeaturesEntity getDoctorsFeaturesEntityByDoctorId(long id);
    DoctorsFeaturesEntity getDoctorsFeaturesEntityById(int id);
//    DoctorsFeaturesEntity getDoctorsFeaturesEntityById(long id);
}
