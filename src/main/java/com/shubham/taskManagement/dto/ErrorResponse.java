package com.shubham.taskManagement.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error_message;
    public ErrorResponse(String error_message) {
        this.error_message = error_message;
    }
}
