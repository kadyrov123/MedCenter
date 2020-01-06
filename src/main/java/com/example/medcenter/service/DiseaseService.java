package com.example.medcenter.service;

import com.example.medcenter.dto.DiseaseDTO;

import java.util.List;

public interface DiseaseService {
    List<DiseaseDTO> getDiseaseByPatientId(long patientId);
    List<DiseaseDTO> getDiseaseByDoctorId(long doctorId);

    void updateDisease(DiseaseDTO diseaseDTO);
    void saveDisease(DiseaseDTO diseaseDTO);

}
