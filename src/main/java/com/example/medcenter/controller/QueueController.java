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
    public @ResponseBody String saveQueue(@RequestParam int doctorId, @RequestParam String userUsername ,@RequestParam String date ,@RequestParam String time , @RequestParam int order ){

        UsersEntity usersEntity = usersRepository.findUsersEntityByUsername(userUsername);
        DoctorsFeaturesEntity doctorsFeaturesEntity = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctorId);

        System.out.println("=================================================="+usersEntity.getId());
        System.out.println("=================================================="+doctorsFeaturesEntity.getIntervalId());
        System.out.println("=================================================="+doctorsFeaturesEntity.getId());

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

//        queue.getDoctorId();
        System.out.println("=================================================="+queue.getDoctorId());
        System.out.println("=================================================="+queue.getIntervalId());

        queueRepository.save(queue);

//        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorId((long)2);

//        System.out.println("=================================================="+queue.getTime());
        return "timetables";
    }

    @RequestMapping(value = "/getTimetableByDoctorId", method = RequestMethod.GET)
    public @ResponseBody List<TimetableDTO> getTimetableByDoctorId(@RequestParam int  doctorId){

//        System.out.println("========================================== doctorId ="+doctorId);
        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorFeaturesId(doctorId);

        return timetables;
    }

//    @RequestMapping(value = "/getCharNum", method = RequestMethod.GET)
//    public @ResponseBody TimeDTO getCharNum(@RequestBody String text) {
//
//        System.out.println("=================================================== TIME = "+text);
//
//        TimeDTO result = new TimeDTO();
//
//        if (text != null) {
//            result.setTime("9:00 - 10:00");
//            result.setFree(false);
//            result.setStatus(1);
//            result.setOrder(1);
//        }
//
//        return result;
//    }


}
