package com.example.inventory.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class QuantityDto {
    @NotNull(message = "product quantity may not be null")
    @PositiveOrZero(message = "product quantity may not be negative")
    private Long quantity;
}
