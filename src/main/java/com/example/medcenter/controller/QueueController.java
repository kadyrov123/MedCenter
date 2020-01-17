package com.example.medcenter.controller;

import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class QueueController {
    @Autowired
    DoctorsService doctorsService;

    @RequestMapping("/queue")
    public String a(ModelMap modelMap){
        Date date = new Date();
        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
        modelMap.addAttribute("queueObject",new QueueEntity());
        return "timetable";
    }

    @RequestMapping(value = "/queue/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveQueue(@RequestParam String time){
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
//        modelMap.addAttribute("queueObject",new QueueEntity());
        System.out.println(time);
        return "timetable";
    }

}
