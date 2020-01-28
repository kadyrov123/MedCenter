package com.example.medcenter.controller;

import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DiseaseRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DiseaseRepository diseaseRepository;

    @RequestMapping("/user/{username}/profile")
    public String userProfilePage(@PathVariable("username") String username , ModelMap modelMap){
        UsersEntity user = usersRepository.findUsersEntityByUsername(username);
        System.out.println(username);
        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
        modelMap.addAttribute("diseases" , diseaseRepository.findDiseaseEntitiesByPatientId(user.getId()));
        modelMap.addAttribute("user" , user);

        return "user/profile";
    }

    @PostMapping("/user/update")
    public String updateUserInfo(UsersEntity user){
//        System.out.println(user.getId());
        user.setPassword(usersRepository.findUsersEntityById(user.getId()).getPassword());
        usersRepository.save(user);
        return "redirect:"+user.getUsername()+"/profile";
    }



}
