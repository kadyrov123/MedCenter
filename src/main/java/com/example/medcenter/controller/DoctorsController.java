package com.example.medcenter.controller;

import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.dto.DoctorDTO;
import com.example.medcenter.entity.*;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.IntervalRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@Controller
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

    @GetMapping("/user/{userId}/profile")
    @PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
    public String getUser(@PathVariable long userId , ModelMap model){
        System.out.println("user/userId/profile" + userId);
        model.addAttribute("user",usersRepository.getOne((long)userId));
        model.addAttribute("visits",userService.getVisitsByUserId(userId));
        model.addAttribute("diseases", diseaseService.getDiseaseByPatientId(userId));
        model.addAttribute("diseaseToChange", new DiseaseDTO());
        return "user/profile";
    }

    @GetMapping("/doctor/profile")
    public String userProfilePage( ModelMap modelMap){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
//        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
//        modelMap.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
//        modelMap.addAttribute("diseaseToChange", new DiseaseDTO());
        modelMap.addAttribute("patient_list", doctorsService.getTodayPatientListByDoctorId(doctor.getId()));
        modelMap.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
        modelMap.addAttribute("doctor" , doctor);
        modelMap.addAttribute("intervals" , intervalRepository.findAll());

        return "doctor/profile";
    }

    @PostMapping("/doctor/update")
    public String updateUserInfo(DoctorsFeaturesEntity doctor){
//        System.out.println(doctor.getIntervalId());
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        UsersEntity doctorUser = doctor.getUsersByDoctorId();
        doctorUser.setId(authorizedUser.getId());
        doctorUser.setPassword(authorizedUser.getPassword());
//        usersRepository.save(doctorUser);

//        IntervalEntity doctorInterval = doctor.getIntervalByIntervalId();
        IntervalEntity doctorInterval = intervalRepository.getOne(doctor.getIntervalId());
//        doctorInterval.setInterval(30);

        Collection<RoleEntity> doctorRoles =  doctor.getUsersByDoctorId().getRoles();
        doctorUser.setRoles(doctorRoles);

        Set<DoctorsTypeEntity> doctorsTypes = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(doctor.getId()).getDoctorsTypeEntities();
//        Collection<QueueEntity> doctorTimetable = doctor.get

        doctor.setUsersByDoctorId(doctorUser);
        doctor.setIntervalByIntervalId(doctorInterval);
        doctor.setDoctorsTypeEntities(doctorsTypes);

        doctorsFeaturesRepository.save(doctor);
        return "redirect:/doctor/profile";
    }

}
