package com.example.inventory.repositories;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.repositories.summary.IdNameOutOfStock;
import com.example.inventory.repositories.summary.TotalAndAverage;
import com.example.inventory.repositories.summary.TotalQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIgnoreCase(String name);

    //Following queries are for statistics

    //Returns name and ids of out of stock
    @Query(value = "SELECT id AS id, name AS name "
            + "FROM Product where quantity = 0", nativeQuery = true)
    List<IdNameOutOfStock> outOfStock();

    //Also returns average
    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity, SUM(CAST(price as DECIMAL(9,2)) * quantity) / SUM(quantity) AS averagePrice "
            + "FROM Product", nativeQuery = true)
    List<TotalAndAverage> totalAverage();

    //Does not return average
    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity "
            + "FROM Product", nativeQuery = true)
    List<TotalAndAverage> total();

    @Query(value = "SELECT SUM(quantity) AS totalQuantity "
            + "FROM Product", nativeQuery = true)
    List<TotalQuantity> totalQuantity();
}
