package com.example.inventory.services;

import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.QuantityDto;
import com.example.inventory.pagination.PaginationRequest;
import com.example.inventory.pagination.PagingResult;

import java.util.List;

public interface InventoryService {

    public ProductDto createProduct(ProductDto dto);

    public PagingResult<ProductDto> getAllProducts(PaginationRequest request);

    public ProductDto searchProductByName(String name);

    public void updateTheQuantityOfAProduct(QuantityDto dto, long id);

    public void deleteAProductById(Long id);

    public Object productSummary();

}
