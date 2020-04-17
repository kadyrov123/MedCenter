package com.example.medcenter.controller;

import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.QueueRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Queue;


@Controller
public class QueueController {
    @Autowired
    DoctorsService doctorsService;

    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    UsersRepository usersRepository;


//    @RequestMapping("/queue")
//    public String a(ModelMap modelMap){
//        Date date = new Date();
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
//        modelMap.addAttribute("queueObject",new QueueEntity());
//        return "timetable";
//    }

    @RequestMapping(value = "/queue/save", method = RequestMethod.GET , consumes = "application/json")
    public String saveQueue(@RequestParam int doctorId, @RequestParam String userUsername ,@RequestParam String date ,@RequestParam String time , @RequestParam int order ){

        UsersEntity usersEntity = usersRepository.findUsersEntityByUsername(userUsername);
        DoctorsFeaturesEntity doctorsFeaturesEntity = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctorId);

        QueueEntity queue = new QueueEntity();
        queue.setDate(Date.valueOf(date));
        queue.setDoctorId(doctorsFeaturesEntity.getId());
        queue.setDoctorFeaturesByDoctorId(doctorsFeaturesEntity);
        queue.setUserId((long)usersEntity.getId());
        queue.setTime(time);
        queue.setOrder(order);
        queue.setIntervalId(doctorsFeaturesEntity.getIntervalId());
        queue.setIntervalByIntervalId(doctorsFeaturesEntity.getIntervalByIntervalId());
        queue.setStatus(1);

        queueRepository.save(queue);

        return "index";
    }

    @RequestMapping(value = "/getTimetableByDoctorId", method = RequestMethod.GET)
    public @ResponseBody List<TimetableDTO> getTimetableByDoctorId(@RequestParam int  doctorId){
        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorFeaturesId(doctorId);
        return timetables;
    }



}
