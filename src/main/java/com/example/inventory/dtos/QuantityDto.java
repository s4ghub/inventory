package com.example.inventory.dtos;

import com.example.inventory.dtos.validation.customannotation.QuantityNonZeroAnnotation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QuantityDto {
    //It can be negative when the products are sold or returned and positive when added to stock
    @QuantityNonZeroAnnotation
    @Min(-10000000L)
    @Max(10000000L)
    private Long quantity;
}
