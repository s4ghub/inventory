package com.example.inventory.dtos;

import com.example.inventory.dtos.validation.customannotation.QuantityNonZeroNonNullAnnotation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * This lightweight dto is update the stock of a product
 * When the value is negative, stock decreases. It means goods are sold or returned.
 * When value is positive, that means new stock arrives.
 */
@Data
public class QuantityDto {
    //It can be negative when the products are sold or returned and positive when added to stock
    @QuantityNonZeroNonNullAnnotation
    @Min(-1000L)
    @Max(1000L)
    private Long quantity;
}
