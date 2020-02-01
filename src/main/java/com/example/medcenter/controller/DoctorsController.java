package com.example.medcenter.controller;

import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        modelMap.addAttribute("doctor" , doctor);

        return "doctor/profile";
    }


}
