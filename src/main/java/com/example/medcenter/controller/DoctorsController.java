package com.example.medcenter.controller;

import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.dto.DoctorDTO;
import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.dto.TimetableDTO;
import com.example.medcenter.entity.*;
import com.example.medcenter.repoitory.*;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
public class DoctorsController {


    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserService userService;
    @Autowired
    DiseaseService diseaseService;
    @Autowired
    DoctorsService doctorsService;
    @Autowired
    IntervalRepository intervalRepository;
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    DiseaseRepository diseaseRepository;


    @GetMapping("/doctor")
    public String test(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/user/{userId}/profile")
    public String getUser(@PathVariable long userId , ModelMap model){
        System.out.println("user/userId/profile" + userId);
        model.addAttribute("user",usersRepository.getOne((long)userId));
        model.addAttribute("visits",userService.getVisitsByUserId(userId));
        model.addAttribute("diseases", diseaseService.getDiseaseByPatientId(userId));
        model.addAttribute("diseaseToChange", new DiseaseDTO());
        return "user/profile";
    }

//    @GetMapping("/doctor/profile")
//    public String userProfilePage( ModelMap modelMap){
//        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
////        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
////        modelMap.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
////        modelMap.addAttribute("diseaseToChange", new DiseaseDTO());
//        modelMap.addAttribute("patient_list", doctorsService.getTodayPatientListByDoctorId(doctor.getId()));
//        modelMap.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
//        modelMap.addAttribute("doctor" , doctor);
//        modelMap.addAttribute("intervals" , intervalRepository.findAll());
//
//        return "doctor/profile";
//    }

    @GetMapping("/doctor/profile")
    public String doctorProfile(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        model.addAttribute("user" , user);
        model.addAttribute("doctor" , doctor);
        model.addAttribute("canBeEdited" , true);
        return "admin/profile";
    }

    @GetMapping("/doctor/timetable")
    public String doctorTimetable(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        model.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
        return "admin/tables";
    }

    @PostMapping("/doctor/update")
    public String updateUserInfo(DoctorsFeaturesEntity doctor){
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        doctor.getUsersByDoctorId().setId(authorizedUser.getId());
        doctor.getUsersByDoctorId().setRoles(authorizedUser.getRoles());
        doctor.getUsersByDoctorId().setPassword(authorizedUser.getPassword());
        usersRepository.save(doctor.getUsersByDoctorId());

        IntervalEntity doctorInterval = intervalRepository.getOne(doctor.getIntervalId());

        Set<DoctorsTypeEntity> doctorsTypes = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctor.getId()).getDoctorsTypeEntities();

        doctor.setUsersByDoctorId(doctor.getUsersByDoctorId());
        doctor.setIntervalByIntervalId(doctorInterval);
        doctor.setDoctorsTypeEntities(doctorsTypes);

        doctorsFeaturesRepository.save(doctor);
        return "redirect:/doctor/profile";
    }


    @RequestMapping(value = "/doctor/save/queue", method = RequestMethod.POST , produces = "application/json" , consumes = "application/json")
//    public @ResponseBody String doctorSaveTime(@RequestBody TimeDTO[] arr , ModelMap modelMap ) {
    public String doctorSaveTime(@RequestBody TimeDTO[] arr  ) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        for(TimeDTO time : arr){

            boolean exists = false;
            long queueId = 0;
            java.sql.Date date  = java.sql.Date.valueOf(time.getDateStr());
            List<QueueEntity> queueEntities = queueRepository.findQueueEntitiesByDate(date);
            for(QueueEntity queue : queueEntities){
                if(queue.getOrder() == time.getOrder()){
                    exists = true;
                    queueId = queue.getId();
                }
            }

            if(!exists) {
                QueueEntity queueEntity = new QueueEntity();

                queueEntity.setDoctorFeaturesByDoctorId(doctor);
                queueEntity.setUserId(doctor.getUsersByDoctorId().getId());
                queueEntity.setDate(date);
                queueEntity.setOrder(time.getOrder());
                queueEntity.setTime(time.getTime());
                queueEntity.setStatus(2);
                queueEntity.setIntervalByIntervalId(doctor.getIntervalByIntervalId());

                queueRepository.save(queueEntity);
            }else{
                QueueEntity queueEntity = queueRepository.getOne(queueId);
                queueEntity.setStatus(0);
                queueRepository.save(queueEntity);
            }
        }

//        modelMap.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));

        return "redirect:/doctor/profile";
    }


    @PostMapping(value = "/doctor/changeDisease")
    public String changeDiseaseInfo(DiseaseEntity diseaseToChange){
        UsersEntity autorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(diseaseToChange.getPatientId());
//        System.out.println(diseaseToChange.getId());
//        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(autorizedUser.getId());

        UsersEntity patient = usersRepository.getOne(diseaseToChange.getPatientId());
        diseaseToChange.setUsersByPatientId(patient);

        java.util.Date today = new java.util.Date();
        diseaseToChange.setDate(new java.sql.Date(today.getTime()));
        diseaseRepository.save(diseaseToChange);

        return "redirect:/user/"+diseaseToChange.getPatientId()+"/profile";
//        if(doctor.getId() == diseaseToChange.getDoctorId()){
//            java.util.Date today = new java.util.Date();
//            diseaseToChange.setDate(new java.sql.Date(today.getTime()));
//            diseaseRepository.save(diseaseToChange);
//            return "redirect:";
//        }else {
//            return "redirect:/doctor/profile";
//        }

//        return "redirect:/doctor/profile";
    }


}
