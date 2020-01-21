package com.example.medcenter.controller;

import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.QueueRepository;
import com.example.medcenter.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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


//    @RequestMapping("/queue")
//    public String a(ModelMap modelMap){
//        Date date = new Date();
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
//        modelMap.addAttribute("queueObject",new QueueEntity());
//        return "timetable";
//    }

    @RequestMapping(value = "/queue/save", method = RequestMethod.GET , consumes = "application/json")
    public @ResponseBody String saveQueue(@RequestParam int doctorId, @RequestParam int userId ,@RequestParam String date ,@RequestParam String time , @RequestParam int order ){
//    public @ResponseBody List<TimetableDTO> saveQueue(@RequestParam int doctorId, @RequestParam int userId ){
//    public @ResponseBody List<TimetableDTO> saveQueue(@RequestBody QueueEntity queue ){
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
//        modelMap.addAttribute("queueObject",new QueueEntity());
        System.out.println("==================================================");

        long millis=System.currentTimeMillis();
        java.sql.Date mydate=new java.sql.Date(millis);
        QueueEntity queue = new QueueEntity();
        queue.setDate(mydate);
        queue.setDoctorId((long)doctorId);
        queue.setUserId((long)userId);
        queue.setTime(time);
        queue.setOrder(order);
        queue.setStatus(1);

        queueRepository.save(queue);

        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorId((long)2);

//        System.out.println("=================================================="+queue.getTime());
        return "timetables";
    }

    @RequestMapping(value = "/getTimetableByDoctorId", method = RequestMethod.GET)
    public @ResponseBody List<TimetableDTO> getTimetableByDoctorId(@RequestParam int  doctorId){

//        System.out.println("========================================== doctorId ="+doctorId);
        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorId((long)2);

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
