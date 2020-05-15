package com.example.medcenter.service;


import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.RoleEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.RoleRepository;
import com.example.medcenter.repoitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersDetailsServiceImpl implements UsersDetailsService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsersEntity findByUsername(String username){
        return userRepository.findUsersEntityByUsername(username);
    }

    public UsersEntity save(UserRegistrationDTO registration){
        UsersEntity user = new UsersEntity();
        user.setName(registration.getFirstName());
        user.setSurname(registration.getLastName());
        user.setUsername(registration.getUsername());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.getRoleEntityByRole("ROLE_USER")));
        return userRepository.save(user);
    }

    public UsersEntity saveDoctor(UserRegistrationDTO registration){
        UsersEntity user = new UsersEntity();
        user.setName(registration.getFirstName());
        user.setSurname(registration.getLastName());
        user.setUsername(registration.getUsername());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.getRoleEntityByRole("ROLE_DOCTOR")));
        return userRepository.save(user);
    }

    @Override
    public boolean changePassword(String currentPassword , String newPassword, UsersEntity usersEntity){
        if(passwordEncoder.matches(currentPassword , usersEntity.getPassword())){
            usersEntity.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(usersEntity);
            return true;
        }
        return false;
    }


    public boolean setPassword(UsersEntity usersEntity , String password ){
        usersEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(usersEntity);
        return true;
    }



    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UsersEntity user = this.userRepository.findUsersEntityByUsername(userName);

        if (user == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

//        System.out.println("Found User: " + user);

        // [ROLE_USER, ROLE_ADMIN,..]
//        List<String> roleNames = this.roleDao.getRoleNames(((com.app.pharmacy.apteka.model.User) user).getId());

//        List<RoleEntity> userRoles = userRoleRepository.findAllByUser(((law.advisor.model.User) user));
        List<RoleEntity> userRoles =(List<RoleEntity>) user.getRoles();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (userRoles != null) {
            for (RoleEntity userRole : userRoles) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(user.getUsername(), //
                user.getPassword(), grantList);

        return userDetails;
    }

}
