package com.example.medcenter.controller;

import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.dto.DoctorDTO;
import com.example.medcenter.dto.TimeDTO;
import com.example.medcenter.entity.*;
import com.example.medcenter.repoitory.DoctorsFeaturesRepository;
import com.example.medcenter.repoitory.IntervalRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
//    @PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
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

        doctor.setUsersByDoctorId(doctorUser);
        doctor.setIntervalByIntervalId(doctorInterval);
        doctor.setDoctorsTypeEntities(doctorsTypes);

        doctorsFeaturesRepository.save(doctor);
        return "redirect:/doctor/profile";
    }


//    @PostMapping("/doctor/save/queue")
////    public String saveTimeToQueue(@RequestParam(value="timeList") Map<String,String>[] timeList){
////    public String saveTimeToQueue(@RequestParam(value="timeList") String[][] timeList){
//    public String saveTimeToQueue(@RequestParam(value= "timeList", required = true) String[][] timeList){
//        System.out.println(timeList[0][0]);
////        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        return "redirect:/doctor/profile";
//    }

//    @PostMapping("/doctor/save/queue")
////    public String saveTimeToQueue(@RequestParam(value="timeList") Map<String,String>[] timeList){
////    public String saveTimeToQueue(@RequestParam(value="timeList") String[][] timeList){
//    public String saveTimeToQueue(@RequestParam(value = "timeList", required = true) String[][] timeList){
//        System.out.println(timeList[0][0]);
////        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        return "redirect:/doctor/profile";
//    }


//    @RequestMapping(value = "/doctor/queue", method = RequestMethod.POST)
//    public @ResponseBody void Submit(@RequestBody String[][] name) {
//        System.out.println(name[0][0]);
//    }

//    @RequestMapping(value = "/doctor/queue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "/doctor/queue", method = RequestMethod.POST, consumes = "application/json")
//    public @ResponseBody void Submit(@RequestParam("myArray") String[][] name) {
//        System.out.println(name[0][0]);
//    }

//    @RequestMapping(value = "/doctor/queue", method = RequestMethod.GET, consumes = "application/json")
//    public @ResponseBody void Submit2() {
//        System.out.println("blabla");
//    }

//    @RequestMapping(value = "/doctor/save/queue", method = RequestMethod.POST , consumes = "application/json")
//    @RequestMapping(value = "/doctor/save/queue", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @RequestMapping(value = "/doctor/save/queue", method = RequestMethod.POST , produces = "application/json")
//    public @ResponseBody void Submit(@RequestParam Map<String,String> arr) {
//    public @ResponseBody void Submit(@RequestBody Map<String,String>[] arr) {
//    public @ResponseBody void Submit(@RequestBody Map<String , Map<String,String>> arr) {
//    public @ResponseBody void Submit(@RequestBody Map<Integer , TimeDTO> arr) {
//    public @ResponseBody void Submit( @RequestBody TimeDTO arr , HttpServletRequest request) {
    public @ResponseBody void Submit(@RequestBody TimeDTO arr ) {
//        System.out.println(arr.get(0).values());
//        for (Integer i : arr.keySet())
//            System.out.println("key: " + i);

        // using values() for iteration over keys
//        System.out.println(arr.size());
//        System.out.println(arr.length);
//        Set<Integer> keys =  arr.keySet();
//        System.out.println(keys.size());
//        for(Integer obj : keys){
//            System.out.println(obj);
//        }
//        System.out.println(arr.get(0).get("date"));
        String a= "";
//        for (Map<String , String> map : arr.values()) {
//            for (String s : map.values()) {
//                a += s;
//            }
//            map.forEach((k,v) -> System.out.println("Key = "
//                    + k + ", Value = " + v));
//            System.out.println(map.get("date"));
//        }
        System.out.println("sdfsdfsd = " +arr);
        System.out.println("sdfsdfsd = " +arr.getTime());
//        System.out.println("sdfsdfsd = " +arr[0].getTime());
//        System.out.println("sdfsdfsd = " +arr[0]);
//        System.out.println("sdfsdfsd = " +arr.get(0));

    }

}
