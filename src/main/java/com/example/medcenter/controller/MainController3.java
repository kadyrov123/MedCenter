package com.example.medcenter.controller;

import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.IntervalRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController3 {

    @Autowired
    IntervalRepository intervalRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    DoctorsService doctorsService;

    @RequestMapping("/ht")
    public String g(){
        return "ht";
    }

//    @RequestMapping(value = "/path-to/hosting/save", method = RequestMethod.POST)
//    public String updateHosting(@RequestBody HostingForm hostingForm) {
//        //...
//    }

    @RequestMapping(value = "/path-to/hosting/save", method = RequestMethod.POST)
    public String updateHosting(@RequestBody String time) {
        //...
        System.out.println("=================================================== TIME = "+time);
        return "ht";
    }

//    @RequestMapping("/doctors")
//    public List<DoctorsFeaturesEntity> g3(){
//        return doctorsFeaturesRepository.findAll();
//    }

//    @GetMapping("/doctors")
//    public List<DoctorsFeaturesEntity> g3(){
//        return doctorsFeaturesRepository.findAll();
//    }
}
