package com.example.medcenter.service;

import com.example.medcenter.dto.QueuePatientsDTO;

import java.util.Date;
import java.util.List;

public interface RegistratorService {
    List<QueuePatientsDTO> getQueuePatientsByDate(Date date);
    List<QueuePatientsDTO> getQueuePatientsForTodayByDoctorId(int doctorId);
}
