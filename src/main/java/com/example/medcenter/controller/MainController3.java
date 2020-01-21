package com.example.medcenter.controller;

import com.example.medcenter.dto.TimeDTO;
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

    @ResponseBody
    @RequestMapping(value = "/path-to/hosting/save", method = RequestMethod.POST)
    public String updateHosting(@RequestParam String time) {
        //...
        System.out.println("=================================================== TIME = "+time);
        return "ht";
    }

    @RequestMapping(value = "/getCharNum", method = RequestMethod.GET)
    public @ResponseBody TimeDTO getCharNum(@RequestParam String text) {

        System.out.println("=================================================== TIME = "+text);

        TimeDTO result = new TimeDTO();

        if (text != null) {
            result.setTime("9:00 - 10:00");
            result.setFree(false);
            result.setStatus(1);
            result.setOrder(1);
        }

        return result;
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
