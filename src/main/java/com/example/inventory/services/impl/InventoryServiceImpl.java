package com.example.inventory.services.impl;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.mapping.DtoEntityMapper;
import com.example.inventory.exceptionhandling.BadInputException;
import com.example.inventory.repositories.ProductRepository;
import com.example.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository repository;
    private final DtoEntityMapper mapper;

    @Autowired
    public InventoryServiceImpl(ProductRepository repository, DtoEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(value = "inventorySqlTransactionManager")
    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product createdNewProduct = repository.save(mapper.dtoToEntity(dto, null));
        return mapper.entityToDto(createdNewProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return List.of();
    }

    @Transactional(value = "inventorySqlTransactionManager", readOnly = true)
    @Override
    public ProductDto searchProductByName(String name) {
        List<Product> products = repository.findByNameIgnoreCase(name);
        if(products == null || products.isEmpty()) {
            throw new BadInputException("The name provided is not correct");
        }
        return mapper.entityToDto(products.get(0));
    }

    @Override
    public void updateTheQuantityOfAProduct(ProductDto dto) {

    }

    @Override
    public void deleteAProductById(Long id) {

    }

    @Override
    public Object productSummary() {
        return null;
    }
}
