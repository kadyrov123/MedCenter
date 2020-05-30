package com.example.medcenter.service;

import com.example.medcenter.dto.DoctorDTO;
import com.example.medcenter.dto.DoctorsPatientDTO;
import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.UsersEntity;

import java.util.Date;
import java.util.List;

public interface DoctorsService {
    List<DoctorDTO> getDoctors();
    DoctorDTO getDoctorById(long id);
    DoctorDTO getDoctorByType(String type);

    void saveDoctor(DoctorDTO doctorDTO);
    void updateDoctor(DoctorDTO doctorDTO);

    List<TimetableDTO> getTimetableByDoctorFeaturesId(int doctorId);
    List<TimeDTO> getTimetableByDoctorFeaturesIdAndDate(int doctorId, Date date);

    List<DoctorsPatientDTO>  getPatientListByDoctorIdAndDate(int doctorId , Date date);
    List<DoctorsPatientDTO>  getTodayPatientListByDoctorId(int doctorId);

    void saveDefaultDoctor(UsersEntity user , int doctorTypeId);

    void changeDoctorWorkingCredentials(int doctorId);

}
