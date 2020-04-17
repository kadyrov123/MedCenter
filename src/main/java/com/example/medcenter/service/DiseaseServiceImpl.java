package com.example.medcenter.service;

import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.entity.DiseaseEntity;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.DoctorsTypeEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DiseaseRepository;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService{

    @Autowired
    DiseaseRepository diseaseRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<DiseaseDTO> getDiseaseByPatientId(long patientId) {

        List<DiseaseEntity> diseaseEntities = diseaseRepository.findDiseaseEntitiesByPatientId(patientId);
        List<DiseaseDTO> diseaseDTOList = new ArrayList<>();
        int i = 1;
        for(DiseaseEntity disease : diseaseEntities){
            DiseaseDTO dto = new DiseaseDTO();

            DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getOne(disease.getDoctorId());
            UsersEntity patient = usersRepository.getOne(disease.getPatientId());

            dto.setDoctorId(doctor.getId());
            dto.setDoctorName(doctor.getUsersByDoctorId().getName());
            dto.setDoctorSurname(doctor.getUsersByDoctorId().getSurname());
            dto.setDoctorEmail(doctor.getUsersByDoctorId().getEmail());

            dto.setPatientId(patient.getId());
            dto.setPatientName(patient.getName());
            dto.setPatientSurname(patient.getSurname());
            dto.setPatientEmail(patient.getEmail());
            dto.setPatientPin(patient.getPin());

            dto.setDate(disease.getDate());
            dto.setDiagnosis(disease.getDiagnosis());
            dto.setRecipe(disease.getRecipe());
            dto.setId((int) disease.getId());
            dto.setFile( disease.getFile());

            diseaseDTOList.add(dto);
            i++;
        }

        return diseaseDTOList;
    }

    @Override
    public List<DiseaseDTO> getDiseaseByDoctorId(long doctorId) {
        return null;
    }

    @Override
    public void updateDisease(DiseaseDTO diseaseDTO) {

    }

    @Override
    public void saveDisease(DiseaseDTO diseaseDTO) {

    }
}
