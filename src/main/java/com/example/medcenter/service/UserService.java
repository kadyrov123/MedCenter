package com.example.medcenter.service;

import com.example.medcenter.dto.PatientVisitsDTO;
import com.example.medcenter.entity.UsersEntity;

import java.sql.Date;
import java.util.List;

public interface UserService {
    UsersEntity findUserByUsername(String username);
    List<PatientVisitsDTO> getVisitsByUserId(long id);
    boolean haveQueueOrder(Date date, Long userId, int doctorId);
}
