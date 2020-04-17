package com.example.medcenter.controller;

import com.example.medcenter.config.SecurityConfiguration;
import com.example.medcenter.dto.DiseaseDTO;
import com.example.medcenter.entity.DiseaseEntity;
import com.example.medcenter.entity.RoleEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.DiseaseRepository;
import com.example.medcenter.repoitory.UsersRepository;
import com.example.medcenter.service.DiseaseService;
import com.example.medcenter.service.UserService;
import com.example.medcenter.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

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
    @Autowired
    DiseaseRepository diseaseRepository;


    private static String FILE_UPLOAD_DIR = "src/main/resources/static/uploads/files/";


    @RequestMapping("/user/profile")
    public String userProfilePage( ModelMap modelMap){
        UsersEntity user = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        UsersEntity user = usersRepository.findUsersEntityByUsername(username);
//        System.out.println(username);
        modelMap.addAttribute("visits" , userService.getVisitsByUserId(user.getId()));
        modelMap.addAttribute("diseases" , diseaseService.getDiseaseByPatientId(user.getId()));
        modelMap.addAttribute("diseaseToChange", new DiseaseDTO());
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
        UsersEntity authorisedUser = usersRepository.findUsersEntityByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean isUser = true;
        for (RoleEntity role : authorisedUser.getRoles()) {
            if(role.getRole().equals("ROLE_DOCTOR")){
                isUser = false;
                break;
            }
        }
        if(newPassword.equals(confirmPassword) ){
            if(usersDetailsService.changePassword(currentPassword , newPassword , user)){
                if(isUser){
                    return "redirect:/user/profile";
                }else {
                    return "redirect:/doctor/profile";
                }
            }
        }
        return "redirect:/user/changepassword?error";
    }


    @GetMapping("/user/changepassword")
    public String changePasswordPage(){
        return "user/changepassword";
    }



    @RequestMapping("/file/download")
    public void downloadPDFResource(@Param(value="id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DiseaseEntity disease = diseaseRepository.getOne(id);
        File file = new File(FILE_UPLOAD_DIR + disease.getFile());
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             *
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }
    }



}
