package com.example.inventory.services.impl;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.QuantityDto;
import com.example.inventory.dtos.mapping.DtoEntityMapper;
import com.example.inventory.exceptionhandling.BadInputException;
import com.example.inventory.exceptionhandling.NotFoundException;
import com.example.inventory.pagination.PaginationRequest;
import com.example.inventory.pagination.PaginationUtils;
import com.example.inventory.pagination.PagingResult;
import com.example.inventory.repositories.ProductRepository;
import com.example.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository repository;
    private final DtoEntityMapper mapper;

    @Autowired
    public InventoryServiceImpl(ProductRepository repository, DtoEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(value = "inventorySqlTransactionManager", isolation = Isolation.REPEATABLE_READ)
    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product createdNewProduct = repository.save(mapper.dtoToEntity(dto, null));
        return mapper.entityToDto(createdNewProduct);
    }

    @Transactional(value = "inventorySqlTransactionManager", readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public PagingResult<ProductDto> getAllProducts(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Page<Product> entities = repository.findAll(pageable);
        final List<ProductDto> dtos = entities.stream().map(mapper::entityToDto).toList();
        return new PagingResult<>(
                dtos,
                entities.getTotalPages(),
                entities.getTotalElements(),
                entities.getSize(),
                entities.getNumber(),
                entities.isEmpty()
        );
    }

    @Transactional(value = "inventorySqlTransactionManager", readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public ProductDto searchProductByName(String name) {
        List<Product> products = repository.findByNameIgnoreCase(name);
        if(products == null || products.isEmpty()) {
            throw new NotFoundException("Product with the name provided is not found");
        }
        return mapper.entityToDto(products.get(0));
    }

    @Transactional(value = "inventorySqlTransactionManager", isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateTheQuantityOfAProduct(QuantityDto dto, long id) {
        Optional<Product> productOptional = repository.findById(id);
        if(productOptional.isEmpty()) {
            throw new NotFoundException("Product with the id provided is not found");
        }
        Product product = productOptional.get();
        Long currentStock = product.getQuantity();
        //Addition will result more than the max value of Long
        if(dto.getQuantity()>0 && (Long.MAX_VALUE - dto.getQuantity())<currentStock) {
            throw new BadInputException(" The quantity is too huge to store");
        }
        //Following may happen when dto.getQuantity() is negative
        //Number of products to be subtracted is more than the current stock
        if((dto.getQuantity() + currentStock)<0) {
            throw new BadInputException(" Stock is less than the quantity to be subtracted");
        }

        product.setQuantity(dto.getQuantity() + currentStock);
    }

    @Transactional(value = "inventorySqlTransactionManager", isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteAProductById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(value = "inventorySqlTransactionManager", readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Object productSummary() {
        return null;
    }
}
