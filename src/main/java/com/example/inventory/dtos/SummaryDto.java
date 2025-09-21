package com.example.inventory.dtos;

import lombok.Data;

@Data
public class SummaryDto {
    Long totalProducts;
    Long totalQuantity;
    String averagePrice;
    OutOfStock[] outOfStock;
}

