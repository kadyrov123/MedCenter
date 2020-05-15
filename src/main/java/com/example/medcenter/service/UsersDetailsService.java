package com.example.medcenter.service;

import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.UsersEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersDetailsService extends UserDetailsService {

    UsersEntity findByUsername(String username);

    UsersEntity save(UserRegistrationDTO registration);
    UsersEntity saveDoctor(UserRegistrationDTO registration);

    boolean changePassword(String currentPassword , String newPassword, UsersEntity usersEntity);
    boolean setPassword(UsersEntity usersEntity , String password);
}
