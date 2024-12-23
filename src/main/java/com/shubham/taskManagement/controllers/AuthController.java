package com.shubham.taskManagement.controllers;

import com.shubham.taskManagement.dao.UserRepo;
import com.shubham.taskManagement.models.UserModel;
import com.shubham.taskManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid @RequestBody  UserModel user) {
        userService.save(user);
        return new ResponseEntity<>("User registered successfully" , HttpStatus.OK);
    }

    @GetMapping("/csrfToken")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken)request.getAttribute("_csrf");
    }

}
