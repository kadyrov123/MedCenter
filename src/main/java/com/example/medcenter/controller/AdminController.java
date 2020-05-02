package com.example.medcenter.controller;

import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.DoctorsTypeEntity;
import com.example.medcenter.entity.IntervalEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.DoctorsTypeRepository;
import com.example.medcenter.repoitory.RoleRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Controller
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UsersDetailsService usersDetailsService;
    @Autowired
    DoctorsService doctorsService;
    @Autowired
    DoctorsTypeRepository doctorsTypeRepository;


    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/admin/profile")
    public String ownprofile(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        model.addAttribute("canBeEdited" , true);
        model.addAttribute("visitsList" , false);
        return "admin/profile";
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
        model.addAttribute("doctorTypes" , doctorsTypeRepository.findAll());
        model.addAttribute("newDoctor" , newDoctor);
        return "admin/doctors";
    }

    @PostMapping("/admin/save/doctor")
    public String saveDoctor(@ModelAttribute("newDoctor") @Valid UserRegistrationDTO newDoctorUser, @RequestParam int doctorTypeId, BindingResult result){
        UsersEntity existing = usersDetailsService.findByUsername(newDoctorUser.getUsername());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()){
            return "redirect:/admin/doctors";
        }

        usersDetailsService.saveDoctor(newDoctorUser);
        doctorsService.saveDefaultDoctor(usersRepository.findUsersEntityByUsername(newDoctorUser.getUsername()), doctorTypeId);

        return "redirect:/admin/doctors";
    }


    @GetMapping("/admin/doctor/{id}/profile")
    public String doctorProfile(@PathVariable int id , ModelMap model) {
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(id);
        UsersEntity user = doctor.getUsersByDoctorId();
        model.addAttribute("doctor" , doctor);
        model.addAttribute("user" , user);
        model.addAttribute("canBeEdited" , false);
        model.addAttribute("visitsList" , false);
        return "admin/profile";
    }

    @GetMapping("/admin/doctor/{id}/delete")
    public String deleteDoctor(@PathVariable int id , ModelMap model) {
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityById(id);
        UsersEntity doctorUser = doctor.getUsersByDoctorId();
        doctorUser.setRoles(new ArrayList<>());
        doctorsFeaturesRepository.delete(doctor);
        usersRepository.delete(doctorUser);
        return "redirect:/admin/doctors";
    }

}
