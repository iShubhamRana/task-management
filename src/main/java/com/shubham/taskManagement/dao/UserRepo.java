package com.shubham.taskManagement.dao;

import com.shubham.taskManagement.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);
}
