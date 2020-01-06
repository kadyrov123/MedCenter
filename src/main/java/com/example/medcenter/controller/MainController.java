package com.example.medcenter.controller;

import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.IntervalEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.IntervalRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    IntervalRepository intervalRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    DoctorsService doctorsService;

    @GetMapping("/")
    public List<IntervalEntity> g(){
        return intervalRepository.findAll();

    }

    @GetMapping("/users")
    public List<UsersEntity> g2(){
        return usersRepository.findAll();
    }
    @GetMapping("/doctors")
    public List<DoctorsFeaturesEntity> g3(){
        return doctorsFeaturesRepository.findAll();
    }
    @GetMapping("/timeList")
    public List<TimeDTO> g4(){
        Date date = new Date();
        return doctorsService.getTimetableByDoctorIdAndDate((long)2 , date);
    }

    @GetMapping("/date")
    public List<TimetableDTO> g5(){
        Date date = new Date();
        return doctorsService.getTimetableByDoctorId((long)2);
    }
}

