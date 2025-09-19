package com.example.inventory.exceptionhandling;

import lombok.NonNull;

public class NotFoundException extends RuntimeException{
    public NotFoundException(@NonNull String message) {
        super(message);
    }
}
