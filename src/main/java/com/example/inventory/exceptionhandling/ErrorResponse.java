package com.example.inventory.exceptionhandling;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private String status;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
