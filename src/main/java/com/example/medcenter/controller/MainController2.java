package com.example.medcenter.controller;

import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class MainController2 {

    @Autowired
    DoctorsService doctorsService;

    @RequestMapping("/")
    public String a(ModelMap modelMap){
        Date date = new Date();
        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2 ));
        return "timetable";
    }


}
