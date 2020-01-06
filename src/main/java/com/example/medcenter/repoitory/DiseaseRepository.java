package com.example.medcenter.repoitory;

import com.example.medcenter.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<DiseaseEntity,Long> {
}
