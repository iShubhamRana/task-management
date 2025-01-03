package com.shubham.taskManagement.service;

import com.shubham.taskManagement.dao.UserRepo;
import com.shubham.taskManagement.models.UserModel;
import com.shubham.taskManagement.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManagerService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserModel user = userRepository.findByEmail(email);

        if(user == null) {
           throw new UsernameNotFoundException(email);
        }

        return new UserPrincipal(user);
    }

}

