package com.example.inventory.repositories.summary;

public interface TotalAndAverage {
    Long getTotalProducts();
    Long getTotalQuantity();
    Double getAveragePrice();
}
