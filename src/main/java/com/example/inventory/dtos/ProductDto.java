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
    @Max(1000L)
    private Long quantity;

    @NotBlank(message = "product price may not be null or empty or spaces")
    @Size(min = 1, max = 10, message = "Invalid price: Must be of 1 - 10 characters: No leadin or trailing spaces")
    private String price;

    public void setPrice(String value){
        this.price = value.trim();
    }

    @Nullable
    private Long version;
}
