package com.example.medcenter.repoitory;

import com.example.medcenter.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity,Long> {
}
