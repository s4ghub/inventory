package com.example.inventory.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDto {

    @Nullable
    private Long id;

    @NotBlank(message = "product name may not be null or empty or spaces")
    @Size(min = 5, max = 30, message = "Invalid Name: Must be of 5 - 30 characters: No leadin or trailing spaces")
    private String name;

    public void setName(String value){
        this.name = value.trim();
    }

    @NotNull(message = "product quantity may not be null")
    @PositiveOrZero(message = "product quantity may not be negative")
    private Long quantity;

    @NotNull(message = "product price may not be null")
    @Positive(message = "product price may not be zero or negative")
    private Double price;

    @Nullable
    private Long version;
}
