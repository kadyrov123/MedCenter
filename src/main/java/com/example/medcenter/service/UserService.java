package com.example.medcenter.service;

import com.example.medcenter.dto.PatientVisitsDTO;
import com.example.medcenter.entity.UsersEntity;

import java.util.List;

public interface UserService {
    UsersEntity findUserByUsername(String username);
    List<PatientVisitsDTO> getVisitsByUserId(long id);
}
