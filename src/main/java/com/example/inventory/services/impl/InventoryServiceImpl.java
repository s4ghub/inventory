package com.example.inventory.services.impl;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.dtos.OutOfStock;
import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.QuantityDto;
import com.example.inventory.dtos.SummaryDto;
import com.example.inventory.dtos.mapping.DtoEntityMapper;
import com.example.inventory.exceptionhandling.BadInputException;
import com.example.inventory.exceptionhandling.NotFoundException;
import com.example.inventory.pagination.PaginationRequest;
import com.example.inventory.pagination.PaginationUtils;
import com.example.inventory.pagination.PagingResult;
import com.example.inventory.repositories.ProductRepository;
import com.example.inventory.repositories.summary.IdNameOutOfStock;
import com.example.inventory.repositories.summary.TotalProductQuantityAndAverage;
import com.example.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
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

        //Following may happen when dto.getQuantity() is negative
        //Number of products to be subtracted is more than the current stock
        if((dto.getQuantity() + currentStock)<0) {
            throw new BadInputException(" Stock may not be less than the quantity to be subtracted");
        }

        product.setQuantity(dto.getQuantity() + currentStock);
    }

    @Transactional(value = "inventorySqlTransactionManager", isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteAProductById(long id) {
        repository.deleteById(id);
    }

    //TODO: Better query may be possible here. But as this is statistics, a bit slow doesn't hurt
    @Transactional(value = "inventorySqlTransactionManager", readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public SummaryDto productSummary() {
        //Get statistis from the repository
        //**********************************
        SummaryDto dtoToReturn = new SummaryDto();
        //Get the data about out of stock from the db
        List<IdNameOutOfStock> outOfStockDataFromDB = repository.outOfStock();
        //Populate the dto with the out-of-stock data
        OutOfStock[] ofs = null;
        if(outOfStockDataFromDB != null && outOfStockDataFromDB.size()>0) {
            ofs = new OutOfStock[outOfStockDataFromDB.size()];
            int i = 0;
            for(IdNameOutOfStock idNm:outOfStockDataFromDB) {
                ofs[i] = new OutOfStock();
                ofs[i].setId(idNm.getId());
                ofs[i].setName(idNm.getName());
                i++;
            }
            dtoToReturn.setOutOfStock(ofs);
        }

        //Get the data about total products, quantity and average from the db
        List<TotalProductQuantityAndAverage> aggregateDataFromDB = null;
        aggregateDataFromDB = repository.totalProductsOnly();


        if(aggregateDataFromDB.get(0).getTotalProducts()>0) { //Product table is not empty
            aggregateDataFromDB = repository.totalProductsAndQuantityOnly();
            if(aggregateDataFromDB.get(0).getTotalQuantity()>0) { //Total quantity > 0, we avoid divide by zero error while calculating the average
                aggregateDataFromDB = repository.totalProductsQuantityAverage();
            }
        }

        if(aggregateDataFromDB != null && aggregateDataFromDB.size()>0) {
            TotalProductQuantityAndAverage ta = aggregateDataFromDB.get(0);
            dtoToReturn.setTotalProducts(ta.getTotalProducts());
            dtoToReturn.setTotalQuantity(ta.getTotalQuantity());
            DecimalFormat df = new DecimalFormat("#.##");
            String avg = null;
            if(ta.getAveragePrice() != null) {
                avg = df.format(ta.getAveragePrice());
            }
            dtoToReturn.setAveragePrice(avg);
        }

        return dtoToReturn;

        //=====old code=====//
        /*List<TotalQuantity> quantity = repository.totalQuantity(); //Total quantity
        List<TotalProductQuantityAndAverage> tas = null;
        if(quantity!=null && quantity.size()>0 && quantity.get(0)!=null && quantity.get(0).getTotalQuantity() == 0) {
            //We cannot do average. divide by zero not possible
            tas = repository.total(); // no average
        } else {
            tas = repository.totalProductsQuantityAverage(); //total quantity with average
        }

        List<IdNameOutOfStock> idNames = repository.outOfStock();
        SummaryDto dto = new SummaryDto();
        //Populate dto to return the statistics
        if(tas != null && tas.size()>0) {
            TotalProductQuantityAndAverage ta = tas.get(0);
            dto.setTotalProducts(ta.getTotalProducts());
            dto.setTotalQuantity(ta.getTotalQuantity());
            dto.setAveragePrice(ta.getAveragePrice());
        }
        OutOfStock[] ofs = null;
        if(idNames != null && idNames.size()>0) {
            ofs = new OutOfStock[idNames.size()];
            int i = 0;
            for(IdNameOutOfStock idNm:idNames) {
                ofs[i] = new OutOfStock();
                ofs[i].setId(idNm.getId());
                ofs[i].setName(idNm.getName());
                i++;
            }
            dto.setOutOfStock(ofs);
        }

        return dto;*/
    }
}
