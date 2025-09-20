package com.example.inventory.services;

import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.QuantityDto;

import java.util.List;

public interface InventoryService {

    public ProductDto createProduct(ProductDto dto);

    public List<ProductDto> getAllProducts();

    public ProductDto searchProductByName(String name);

    public void updateTheQuantityOfAProduct(QuantityDto dto, long id);

    public void deleteAProductById(Long id);

    public Object productSummary();

}
