package com.shubham.taskManagement.service;

import com.shubham.taskManagement.dao.UserRepo;
import com.shubham.taskManagement.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepository;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void save(UserModel user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
