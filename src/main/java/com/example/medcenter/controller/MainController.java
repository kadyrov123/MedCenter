package com.example.medcenter.controller;

import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.IntervalEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.IntervalRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    IntervalRepository intervalRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    DoctorsService doctorsService;

    @RequestMapping("/")
    public String home(ModelMap modelMap){
        Date date = new Date();
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
        modelMap.addAttribute("queueObject",new QueueEntity());
        modelMap.addAttribute("doctors",doctorsFeaturesRepository.findAll());
        return "index";
    }

    @RequestMapping("/doctors")
    public String  doctorsPage(){
        return "doctors";
    }


    @RequestMapping("/about")
    public String  aboutPage(){
        return "about";
    }

    @RequestMapping("/departments")
    public String  departmentPage(){
        return "departments";
    }


    @RequestMapping("/blog")
    public String  blogPage(){
        return "blog-home";
    }


    @RequestMapping("/elements")
    public String  elementsPage(){
        return "elements";
    }


    @RequestMapping("/features")
    public String  featurePage(){
        return "features";
    }

    @RequestMapping("/contact")
    public String  contactPage(){
        return "contact";
    }


    @RequestMapping("/blog-single")
    public String  blogSinglePage(){
        return "blog-single";
    }


    @RequestMapping("/queue")
    public String  queue(ModelMap modelMap){
        Date date = new Date();
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
        modelMap.addAttribute("queueObject",new QueueEntity());
        return "timetable";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin/dashboard";
    }
    @GetMapping("/test")
    public String test(Model model) {
        return "admin/test";
    }




}

