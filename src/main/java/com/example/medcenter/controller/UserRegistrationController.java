package com.example.medcenter.controller;


import javax.validation.Valid;

import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UsersDetailsService usersDetailsService;

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDto() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDTO userDto, BindingResult result){

        UsersEntity existing = usersDetailsService.findByUsername(userDto.getUsername());

        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()){
            return "registration";
        }

        usersDetailsService.save(userDto);
        return "redirect:/registration?success";
    }

}