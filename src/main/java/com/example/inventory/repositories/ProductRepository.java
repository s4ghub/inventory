package com.example.inventory.repositories;

import com.example.inventory.domainmodels.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIgnoreCase(String name);
}
