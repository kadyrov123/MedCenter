package com.example.medcenter.controller;

import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorsController {


    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;

//    @RequestMapping("/doctors")
//    public String getDoctorsList(ModelMap modelMap){
//         modelMap.addAttribute("doctors",doctorsFeaturesRepository.findAll());
//        return "";
//    }

//    @RequestMapping("/doctor/id")
//    public String getDoctorById(@PathVariable("id") int id, ModelMap modelMap){
//        modelMap.addAttribute("doctor",doctorsFeaturesRepository.findById(id));
//        return "";
//    }
//
//    @RequestMapping("/doctor/typeId")
//    public String getDoctorByType(@PathVariable("type") int typeId, ModelMap modelMap){
//        modelMap.addAttribute("doctor",doctorsFeaturesRepository.findById(typeId));
//        return "";
//    }


}
