package com.example.medcenter.service;

import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.UsersEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UsersEntity findByUsername(String username);

    UsersEntity save(UserRegistrationDTO registration);
}
