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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String  doctorsPage(ModelMap model){
        model.addAttribute("doctors",doctorsFeaturesRepository.findAll());
        return "doctors";
    }

    @RequestMapping("/guest/doctor/{id}/profile")
    public String  doctorProfile(@PathVariable int id, ModelMap model){
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getOne(id);
        UsersEntity user = doctor.getUsersByDoctorId();
        model.addAttribute("doctor",doctor);
        model.addAttribute("user",user);
        model.addAttribute("timetable",doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
        model.addAttribute("canBeEdited",false);
        return "user/profile";
    }


    @RequestMapping("/about")
    public String  aboutPage(){
        return "about";
    }

    @RequestMapping("/layout")
    public String  aboutPage2(){
        return "layout";
    }

    @RequestMapping("/departments")
    public String  departmentPage(){
        return "departments";
    }

    @RequestMapping("/services")
    public String  servicePage(){
        return "services";
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







}

