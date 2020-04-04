package com.example.medcenter.controller;

import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.DoctorsTypeEntity;
import com.example.medcenter.entity.IntervalEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    UsersDetailsService usersDetailsService;
    @Autowired
    DoctorsService doctorsService;

    @GetMapping("/admin/profile")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public String ownprofile(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        return "admin/ownprofile";
    }


    @PostMapping("/admin/save/profile")
    public String updateUserInfo(UsersEntity admin){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        user.setUsername(admin.getUsername());
        user.setEmail(admin.getEmail());
        user.setName(admin.getName());
        user.setSurname(admin.getSurname());
        System.out.println(admin.getName());

        usersRepository.save(user);

        return "redirect:/admin/profile";
    }

    @GetMapping("/admin/doctors")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public String getDoctors(Model model) {
//        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<DoctorsFeaturesEntity>  doctors = doctorsFeaturesRepository.findAll();
        UserRegistrationDTO newDoctor = new UserRegistrationDTO();
        model.addAttribute("doctors" , doctors);
        model.addAttribute("newDoctor" , newDoctor);
        return "admin/doctors";
    }

    @PostMapping("/save/doctor")
    public String saveDoctor(@ModelAttribute("newDoctor") @Valid UserRegistrationDTO newDoctorUser, BindingResult result){
        UsersEntity existing = usersDetailsService.findByUsername(newDoctorUser.getUsername());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()){
            return "redirect:/admin/doctors";
        }

        usersDetailsService.saveDoctor(newDoctorUser);
        doctorsService.saveDefaultDoctor(usersRepository.findUsersEntityByUsername(newDoctorUser.getUsername()));

        return "redirect:/admin/doctors";
    }

}
