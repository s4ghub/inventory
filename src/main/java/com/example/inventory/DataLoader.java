package com.example.inventory;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final ProductRepository productRepository;


    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        Product product = new Product();
        product.setName("Laptop");
        product.setQuantity(10l);
        product.setPrice(1299.99);
        productRepository.save(product);
    }
}
