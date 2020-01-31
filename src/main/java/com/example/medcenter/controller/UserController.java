package com.example.medcenter.controller;

import com.example.medcenter.config.SecurityConfiguration;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DiseaseRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.UserService;
import com.example.medcenter.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DiseaseService diseaseService;
    @Autowired
    UsersDetailsService usersDetailsService;


    @RequestMapping("/user/profile")
    public String userProfilePage( ModelMap modelMap){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        UsersEntity user = usersRepository.findUsersEntityByUsername(username);
//        System.out.println(username);
        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
        modelMap.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
        modelMap.addAttribute("user" , user);

        return "user/profile";
    }

    @PostMapping("/user/update")
    public String updateUserInfo(UsersEntity user){
//        System.out.println(user.getId());
        user.setPassword(usersRepository.findUsersEntityById(user.getId()).getPassword());
        usersRepository.save(user);
        return "redirect:/user/profile";
    }

    @PreAuthorize("hasAnyRole()")
    @PostMapping("/user/changepassword")
    public String changeUserPassword( @RequestParam String currentPassword,
                                     @RequestParam String newPassword,@RequestParam String confirmPassword){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(newPassword.equals(confirmPassword) ){
            if(usersDetailsService.changePassword(currentPassword , newPassword , user)){
                return "redirect:/user/profile";
            }
        }
        return "redirect:/user/changepassword?error";
    }


    @GetMapping("/user/changepassword")
    public String changePasswordPage(){
        return "user/changepassword";
    }



}
