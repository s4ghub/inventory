package com.example.inventory.exceptionhandling;

import lombok.NonNull;

public class BadInputException extends RuntimeException{
    public BadInputException(@NonNull String message) {
        super(message);
    }
}
