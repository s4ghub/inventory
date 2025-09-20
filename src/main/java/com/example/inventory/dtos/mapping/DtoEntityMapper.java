package com.example.inventory.dtos.mapping;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.dtos.ProductDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DtoEntityMapper {

    public Product dtoToEntity(@NonNull ProductDto dto, Product entity) {
        if(entity == null) {
            entity = new Product();
        }
        entity.setName(dto.getName());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public ProductDto entityToDto(@NonNull Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setVersion(entity.getVersion());
        return dto;
    }
}
