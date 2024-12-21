package com.shubham.taskManagement.models;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumbers;
    private String password;
}
