package com.example.inventory.repositories;

import com.example.inventory.domainmodels.Product;
import com.example.inventory.repositories.summary.IdNameOutOfStock;
import com.example.inventory.repositories.summary.TotalProductQuantityAndAverage;
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

    //Returns total products only
    @Query(value = "SELECT count(*) AS totalProducts "
            + "FROM Product", nativeQuery = true)
    List<TotalProductQuantityAndAverage> totalProductsOnly();

    //Returns total products and quantity only
    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity "
            + "FROM Product", nativeQuery = true)
    List<TotalProductQuantityAndAverage> totalProductsAndQuantityOnly();

    //Returns total products, quantity and Average
    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity, SUM(CAST(price as DECIMAL(9,2)) * quantity) / SUM(quantity) AS averagePrice "
            + "FROM Product", nativeQuery = true)
    List<TotalProductQuantityAndAverage> totalProductsQuantityAverage();

}
