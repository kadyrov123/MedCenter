package com.example.medcenter.controller;

import com.example.medcenter.dto.*;
import com.example.medcenter.entity.*;
import com.example.medcenter.repoitory.*;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.DoctorsService;
import com.example.medcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.util.StringUtils.*;

@Controller
@PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
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
    @Autowired
    QueueRepository queueRepository;
    @Autowired
    DiseaseRepository diseaseRepository;

    private static String FILE_UPLOAD_DIR = "src/main/resources/static/uploads/files/";
    private static String PHOTO_UPLOAD_DIR = "src/main/resources/static/uploads/photos/";


    @GetMapping("/doctor")
    public String test(Model model) {
        return "redirect:/doctor/patients";
    }

    @GetMapping("/user/{userId}/profile")
    public String getUser(@PathVariable long userId , ModelMap model){
        System.out.println("user/userId/profile" + userId);
        model.addAttribute("user",usersRepository.getOne((long)userId));
        model.addAttribute("visits",userService.getVisitsByUserId(userId));
        model.addAttribute("diseases", diseaseService.getDiseaseByPatientId(userId));
        model.addAttribute("diseaseToChange", new DiseaseDTO());
        return "user/profile";
    }

//    @GetMapping("/doctor/profile")
//    public String userProfilePage( ModelMap modelMap){
//        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
////        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
////        modelMap.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
////        modelMap.addAttribute("diseaseToChange", new DiseaseDTO());
//        modelMap.addAttribute("patient_list", doctorsService.getTodayPatientListByDoctorId(doctor.getId()));
//        modelMap.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
//        modelMap.addAttribute("doctor" , doctor);
//        modelMap.addAttribute("intervals" , intervalRepository.findAll());
//
//        return "doctor/profile";
//    }

    @GetMapping("/doctor/profile")
    public String doctorProfile(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        model.addAttribute("user" , user);
        model.addAttribute("doctor" , doctor);
        model.addAttribute("canBeEdited" , true);
        model.addAttribute("visitsList" , false);
        model.addAttribute("intervals" , intervalRepository.findAll());
        model.addAttribute("role" , "doctor");
        return "admin/profile";
    }

    @GetMapping("/doctor/timetable")
    public String doctorTimetable(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        model.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));
        return "admin/tables";
    }

    @GetMapping("/doctor/patients")
    public String doctorPatients(Model model) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        System.out.println(user.getId());
        System.out.println(doctor.getId());
        model.addAttribute("patient_list", doctorsService.getTodayPatientListByDoctorId(doctor.getId()));
        model.addAttribute("doctor", doctor);
        return "admin/patients";
    }

    @PostMapping("/doctor/save/profile")
    public String updateUserInfo(DoctorsFeaturesEntity updatedDoctor ,UsersEntity user, @RequestParam("doctorsPhoto") MultipartFile photo, RedirectAttributes redirectAttributes){
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        user.setRoles(authorizedUser.getRoles());
        user.setId(authorizedUser.getId());
        user.setPassword(authorizedUser.getPassword());
        usersRepository.save(user);

        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getOne(updatedDoctor.getId());
//        updatedDoctor.setUsersByDoctorId(user);
//        doctor.setUsersByDoctorId(user);

//        System.out.println(0 < LocalTime.now() .compareTo(doctor.getEndTime().toLocalTime()));
        boolean workingTimeCannotBeCanged = false;
        if (doctor.getStartTime().compareTo(updatedDoctor.getStartTime()) != 0
                    || doctor.getEndTime().compareTo(updatedDoctor.getEndTime()) != 0
                    || doctor.getIntervalByIntervalId().getId() != updatedDoctor.getIntervalByIntervalId().getId()) {

            if (0 < LocalTime.now() .compareTo(doctor.getEndTime().toLocalTime())) {
//                System.out.println("credentials changes");
                IntervalEntity doctorInterval = intervalRepository.getOne(updatedDoctor.getIntervalByIntervalId().getId());

//                Set<DoctorsTypeEntity> doctorsTypes = doctorsFeaturesRepository.getOne(updatedDoctor.getId()).getDoctorsTypeEntities();

//                doctor.setUsersByDoctorId(updatedDoctor.getUsersByDoctorId());
                doctor.setIntervalByIntervalId(doctorInterval);
//                doctor.setDoctorsTypeEntities(doctorsTypes);
                doctor.setStartTime(updatedDoctor.getStartTime());
                doctor.setEndTime(updatedDoctor.getEndTime());

                doctorsService.changeDoctorWorkingCredentials(doctor.getId());
            }
            else{
                workingTimeCannotBeCanged = true;
            }
        }

            // normalize the file path
        String fileName = cleanPath(photo.getOriginalFilename());

        if (!fileName.equals("")) {
            // save the file on the local file system
            try {
                File oldPhoto = new File(PHOTO_UPLOAD_DIR + doctor.getPhoto());
                if (oldPhoto.delete())                      //returns Boolean value
                {
                    System.out.println(oldPhoto.getName() + " deleted");   //getting and printing the file name
                }
                Path path = Paths.get(PHOTO_UPLOAD_DIR + fileName);
                Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            doctor.setPhoto(fileName);
        } else {
//            updatedDoctor.setPhoto(doctor.getPhoto());
        }

        if(workingTimeCannotBeCanged){
            redirectAttributes.addFlashAttribute("message", "Вы можете поменять время работы только после оканчания рабочего времени !!!");
            redirectAttributes.addFlashAttribute("type", "danger");
        }else {
            redirectAttributes.addFlashAttribute("message", "Вы успешно сохранили");
            redirectAttributes.addFlashAttribute("type", "success");
        }

        doctorsFeaturesRepository.save(doctor);
        return "redirect:/doctor/profile";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileToUpload") MultipartFile file,DiseaseEntity disease, RedirectAttributes attributes) {
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(authorizedUser.getId());

        // normalize the file path
        String fileName = cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(FILE_UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        disease.setDate(new java.sql.Date(new java.util.Date().getTime()));
        disease.setUsersByPatientId(usersRepository.getOne(disease.getPatientId()));
        disease.setFile(fileName);
        disease.setDoctorId(doctor.getId());

        diseaseRepository.save(disease);

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/doctor/patient/"+disease.getPatientId()+"/profile";
    }

    @RequestMapping(value = "/doctor/save/queue", method = RequestMethod.POST , produces = "application/json" , consumes = "application/json")
//    public @ResponseBody String doctorSaveTime(@RequestBody TimeDTO[] arr , ModelMap modelMap ) {
    public String doctorSaveTime(@RequestBody TimeDTO[] arr  ) {
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(user.getId());
        for(TimeDTO time : arr){

            boolean exists = false;
            long queueId = 0;
            java.sql.Date date  = java.sql.Date.valueOf(time.getDateStr());
            List<QueueEntity> queueEntities = queueRepository.findQueueEntitiesByDate(date);
            for(QueueEntity queue : queueEntities){
                if(queue.getOrder() == time.getOrder()){
                    exists = true;
                    queueId = queue.getId();
                }
            }

            if(!exists) {
                QueueEntity queueEntity = new QueueEntity();

                queueEntity.setDoctorFeaturesByDoctorId(doctor);
                queueEntity.setUserId(doctor.getUsersByDoctorId().getId());
                queueEntity.setDate(date);
                queueEntity.setOrder(time.getOrder());
                queueEntity.setTime(time.getTime());
                queueEntity.setStatus(2);
                queueEntity.setIntervalByIntervalId(doctor.getIntervalByIntervalId());

                queueRepository.save(queueEntity);
            }else{
                QueueEntity queueEntity = queueRepository.getOne(queueId);
                queueEntity.setStatus(0);
                queueRepository.save(queueEntity);
            }
        }

//        modelMap.addAttribute("timetable", doctorsService.getTimetableByDoctorFeaturesId(doctor.getId()));

        return "redirect:/doctor/profile";
    }


    @PostMapping(value = "/doctor/changeDisease")
    public String changeDiseaseInfo(@RequestParam("fileToUpload") MultipartFile file, DiseaseEntity disease, RedirectAttributes attributes){

        // normalize the file path
        String fileName = cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(FILE_UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsersEntity patient = usersRepository.getOne(disease.getPatientId());
        disease.setUsersByPatientId(patient);

        java.util.Date today = new java.util.Date();
        disease.setDate(new java.sql.Date(today.getTime()));
        disease.setFile(fileName);
        diseaseRepository.save(disease);

        return "redirect:/doctor/patient/"+disease.getPatientId()+"/profile";
    }


    @RequestMapping(value = "/doctor/getPatientList", method = RequestMethod.GET)
    public @ResponseBody List<List<String>> getTimetableByDoctorId(@RequestParam int  doctorId , @RequestParam("date") String stringDate) throws ParseException {
        java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        List<DoctorsPatientDTO> patient_list = doctorsService.getPatientListByDoctorIdAndDate(doctorId,date);
        List patients = new ArrayList();
        for(int i =0 ; i< patient_list.size() ; i++){
            List<String> patient = new ArrayList<>();
            patient.add(patient_list.get(i).getId()+"");
            patient.add(patient_list.get(i).getPatient().getSurname() +" "+patient_list.get(i).getPatient().getName());
            patient.add(patient_list.get(i).getQueue().getDate()+"");
            patient.add(patient_list.get(i).getQueue().getTime());
            patient.add(patient_list.get(i).getPatient().getId()+"");

            patients.add(patient);
        }
        return patients;
    }

    @RequestMapping(value = "/doctor/searchPatient", method = RequestMethod.GET)
    public @ResponseBody List<List<String>> patientSearch(@RequestParam("value") String value) {

        List<UsersEntity> users = usersRepository.search(value.toLowerCase());

        List patients = new ArrayList();
        for(int i =0 ; i< users.size() ; i++){
            if(isUser((List)users.get(i).getRoles())) {
                List<String> patient = new ArrayList<>();
                patient.add(i+"");
                patient.add(users.get(i).getSurname() + " " + users.get(i).getName());
                patient.add("");
                patient.add("");
                patient.add(users.get(i).getId()+"");

                patients.add(patient);
            }
        }
        return patients;
    }


    @GetMapping("/doctor/patient/{id}/profile")
    public String doctorProfile(@PathVariable int id , ModelMap model) {
        UsersEntity authorizedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        DoctorsFeaturesEntity doctor = doctorsFeaturesRepository.getDoctorsFeaturesEntityByDoctorId(authorizedUser.getId());

        UsersEntity user = usersRepository.findUsersEntityById(id);
        DiseaseEntity newDisease = new DiseaseEntity();
        newDisease.setUsersByPatientId(user);
//        model.addAttribute("doctor" , doctor);
        model.addAttribute("user" , user);
        model.addAttribute("canBeEdited" , false);
        model.addAttribute("visitsList" , true);
        model.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
        model.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
        model.addAttribute("disease" , newDisease);
        model.addAttribute("role" , "null");
        return "admin/profile";
    }





    @RequestMapping("/doctor/file/delete")
    public String deleteFileFromDisease(@Param(value="id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DiseaseEntity disease = diseaseRepository.getOne(id);

        File file = new File(FILE_UPLOAD_DIR + disease.getFile());
        file.delete();

        disease.setFile(null);
        diseaseRepository.save(disease);

        return "redirect:/doctor/patient/"+disease.getPatientId()+"/profile";
    }

    private boolean isUser(List<RoleEntity> roles) {
        for(RoleEntity role: roles){
            if (role.getRole().equals("ROLE_USER")){
                return true;
            }
        }
        return false;
    }


}
