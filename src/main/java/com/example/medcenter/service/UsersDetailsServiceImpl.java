package com.example.medcenter.service;


import com.example.medcenter.dto.UserRegistrationDTO;
import com.example.medcenter.entity.RoleEntity;
import com.example.medcenter.entity.UsersEntity;
import com.example.medcenter.repoitory.RoleRepository;
import com.example.medcenter.repoitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
//        user.setRoles(Arrays.asList(new RoleEntity("ROLE_USER")));
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findUsersEntityByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

//        Collection<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        if (user.getRoleById() != null) {
//            // ROLE_USER, ROLE_ADMIN,..
//            GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleById().getRole());
//            grantList.add(authority);
//
//        }

        Collection<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity());
//        roles.add(user.getRoles());

//        UserDetails userDetails =  new UserDetails(user.getUsername(), //
//                user.getPassword(), grantList);
//        UserDetails userDetails =  new UserDetails();

//        return  userDetails;
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(),
//                mapRolesToAuthorities((Collection<RoleEntity>) roles));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
