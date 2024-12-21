package com.shubham.taskManagement.service;

import com.shubham.taskManagement.models.UserModel;
import com.shubham.taskManagement.models.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManagerService implements UserDetailsService {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("here");

        UserModel user =new UserModel(
                1,
                "Shubham",
                "Rana",
                "rana2001shubham@gmail.com",
                "9821646153",
                "password"
        );
        return new UserPrincipal(user);
    }

}

