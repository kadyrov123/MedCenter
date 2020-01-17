package com.example.medcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController3 {

    @RequestMapping("/")
    public String g(){
        return "ht";
    }

//    @RequestMapping(value = "/path-to/hosting/save", method = RequestMethod.POST)
//    public String updateHosting(@RequestBody HostingForm hostingForm) {
//        //...
//    }

    @RequestMapping(value = "/path-to/hosting/save", method = RequestMethod.POST)
    public String updateHosting(@RequestBody String time) {
        //...
        System.out.println("=================================================== TIME = "+time);
        return "ht";
    }
}
