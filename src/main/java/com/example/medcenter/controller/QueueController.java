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
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    UserService userService;


//    @RequestMapping("/queue")
//    public String a(ModelMap modelMap){
//        Date date = new Date();
//        modelMap.addAttribute("timetable",doctorsService.getTimetableByDoctorId((long)2));
//        modelMap.addAttribute("queueObject",new QueueEntity());
//        return "timetable";
//    }

    @RequestMapping(value = "/queue/save", method = RequestMethod.GET , consumes = "application/json")
    public String saveQueue(@RequestParam int doctorId, @RequestParam String userUsername ,@RequestParam String date ,@RequestParam String time , @RequestParam int order ){
//        UsersEntity usersEntity = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        UsersEntity usersEntity = userService.findUserByUsername(userUsername);
        DoctorsFeaturesEntity doctorsFeaturesEntity = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctorId);
        System.out.println(doctorId);
        System.out.println(doctorsFeaturesEntity);
        if(!userService.haveQueueOrder(Date.valueOf(date),usersEntity.getId(), doctorId)) {
            QueueEntity queue = new QueueEntity();
            queue.setDate(Date.valueOf(date));
//            queue.setDoctorId(doctorsFeaturesEntity.getId());
            queue.setDoctorFeaturesByDoctorId(doctorsFeaturesEntity);
            queue.setUserId(usersEntity.getId());
            queue.setTime(time);
            queue.setOrder(order);
            queue.setIntervalId(doctorsFeaturesEntity.getIntervalId());
            queue.setIntervalByIntervalId(doctorsFeaturesEntity.getIntervalByIntervalId());
            queue.setStatus(1);

            queueRepository.save(queue);
        }

        return "index";
    }

    @GetMapping("/user/queue/{id}/cancel")
    public String cancelQueueOrder(@PathVariable(value="id") Long queueId) {
        UsersEntity authorisedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        QueueEntity queue = queueRepository.getOne(queueId);
        if(queue.getUserId() == authorisedUser.getId()){
//            queue.setStatus(0);
//            queueRepository.save(queue);
            queueRepository.delete(queue);
        }
        return "redirect:/user/profile";
    }

    @RequestMapping(value = "/getTimetableByDoctorId", method = RequestMethod.GET)
    public @ResponseBody List<TimetableDTO> getTimetableByDoctorId(@RequestParam int  doctorId){
        List<TimetableDTO> timetables = doctorsService.getTimetableByDoctorFeaturesId(doctorId);
        return timetables;
    }





}
