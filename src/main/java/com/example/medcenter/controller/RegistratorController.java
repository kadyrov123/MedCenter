package com.example.medcenter.controller;

import com.example.medcenter.dto.QueuePatientsDTO;
import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.entity.DoctorsFeaturesEntity;
import com.example.medcenter.entity.NewComersEntity;
import com.example.medcenter.entity.QueueEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.NewComersRepository;
import com.example.medcenter.repoitory.QueueRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.RegistratorService;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@PreAuthorize(value = "hasRole('ROLE_REGISTRATOR')")
public class RegistratorController {

    @Autowired
    RegistratorService registratorService;
    @Autowired
    DoctorsFeaturesRepository doctorsFeaturesRepository;
    @Autowired
    DoctorsService doctorsService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    NewComersRepository newComersRepository;



    @GetMapping("/registrator")
    public String registrator(ModelMap model){
        model.addAttribute("patientList", registratorService.getQueuePatientsForTodayByDoctorId(1));
//        model.addAttribute("newPatient", new QueuePatientsDTO());
        model.addAttribute("doctors", doctorsFeaturesRepository.findAll());
        return "admin/registrator";
    }

    @GetMapping("/registrator/getPatientList")
    public @ResponseBody List<List<String>> getPatientsByDoctorId(@RequestParam("doctorId") int doctorId){
        List<QueuePatientsDTO> patientsList = registratorService.getQueuePatientsForTodayByDoctorId(doctorId);
        List<List<String>> patients = new ArrayList<>();
        for (QueuePatientsDTO patient: patientsList) {
            List<String> p = new ArrayList<>();
            p.add(patient.getQueueEntity().getId()+"");
            p.add(patient.getSurname()+" "+patient.getName());
            p.add(patient.getQueueEntity().getTime());
            p.add(patient.getCanBeCanceled()+"");
            patients.add(p);
        }
        return  patients;
    }


    @GetMapping("/registrator/getTimeListByDoctorId")
    public @ResponseBody List<TimeDTO> getTimeListByDoctorId(@RequestParam("doctorId") int doctorId){
        List<TimeDTO> times = doctorsService.getTimetableByDoctorFeaturesIdAndDate(doctorId , new Date());
        return  times;
    }

    @PostMapping("/registrator/save/patient")
    public String savePatientToQueue(@RequestParam int doctorId, @RequestParam int order , @RequestParam String firstname ,@RequestParam String lastname){
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getOne(doctorId);
        QueueEntity queue = new QueueEntity();
        queue.setStatus(3);
        queue.setDate(new java.sql.Date(new java.util.Date().getTime()));
        queue.setDoctorFeaturesByDoctorId(doctor);
        queue.setOrder(order);
        queue.setUserId(authorizedUser.getId());
        queue.setIntervalByIntervalId(doctor.getIntervalByIntervalId());

        List<TimeDTO> times = doctorsService.getTimetableByDoctorFeaturesIdAndDate(doctorId,new Date());
        for(TimeDTO t : times){
            if(t.getOrder() == order){
                queue.setTime(t.getTime());
            }
        }

        QueueEntity q = queueRepository.saveAndFlush(queue);

        NewComersEntity patient = new NewComersEntity();
        patient.setName(firstname);
        patient.setSurname(lastname);
        patient.setQueue(q);
        newComersRepository.save(patient);

        return "redirect:/registrator";
    }

    @GetMapping("/registrator/queue/{id}/cancel")
    public String deleteDoctor(@PathVariable Long id , ModelMap model) {
        QueueEntity queue = queueRepository.getOne(id);
        NewComersEntity patient = newComersRepository.findNewComersEntityByQueue(queue);

        queueRepository.delete(queue);
        if(patient != null) {
            newComersRepository.delete(patient);
        }

        return "redirect:/registrator";
    }

    @GetMapping("/registrator/profile")
    public String doctorProfile(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        model.addAttribute("canBeEdited" , true);
        model.addAttribute("visitsList" , false);
        model.addAttribute("role" , "registrator");
        return "admin/profile";
    }

    @PostMapping("/registrator/save/profile")
    public String updateUserInfo(UsersEntity registrator){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUsername(registrator.getUsername());
        user.setEmail(registrator.getEmail());
        user.setName(registrator.getName());
        user.setSurname(registrator.getSurname());
        user.setPin(registrator.getPin());

        usersRepository.save(user);

        return "redirect:/registrator/profile";
    }

}
