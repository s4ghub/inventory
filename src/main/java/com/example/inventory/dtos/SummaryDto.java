package com.example.inventory.dtos;

import lombok.Data;

@Data
public class SummaryDto {
    Long totalProducts;
    Long totalQuantity;
    Double averagePrice;
    OutOfStock[] outOfStock;
}

