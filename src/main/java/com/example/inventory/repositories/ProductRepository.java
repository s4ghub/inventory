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

    /*
    @Query(value = "SELECT p.count(*) AS totalProducts, SUM(p.quantity) AS totalQuantity, SUM(CAST(p.Price as DECIMAL(9,2)) * p.Quantity) / SUM(p.Quantity) AS averagePrice "
            + "FROM product AS p", nativeQuery = true)
    List<TotalAndAverage> totalAverage();


          SELECT
    count(*) AS TotalProducts,
    SUM(Quantity) AS TotalQuantity,
    SUM(CAST(Price as DECIMAL(9,2)) * Quantity) / SUM(Quantity) AS averagePrice
FROM
    Product;

    @Query(value = "SELECT c.year AS yearComment, COUNT(c.*) AS totalComment "
  + "FROM comment AS c GROUP BY c.year ORDER BY c.year DESC", nativeQuery = true)
List<ICommentCount> countTotalCommentsByYearNative();




    SELECT
    id,
    name
FROM
    Product where quantity = 0;

    @Query(value = "SELECT p.id AS id, p.name AS name "
  + "FROM product AS p where p.quantity = 0", nativeQuery = true)
List<IdNameOutOfStock> outOfStock();
    * */
    @Query(value = "SELECT id AS id, name AS name "
            + "FROM Product where quantity = 0", nativeQuery = true)
    List<IdNameOutOfStock> outOfStock();

    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity, SUM(CAST(price as DECIMAL(9,2)) * quantity) / SUM(quantity) AS averagePrice "
            + "FROM Product", nativeQuery = true)
    List<TotalAndAverage> totalAverage();

    @Query(value = "SELECT count(*) AS totalProducts, SUM(quantity) AS totalQuantity "
            + "FROM Product", nativeQuery = true)
    List<TotalAndAverage> total();

    @Query(value = "SELECT SUM(quantity) AS totalQuantity "
            + "FROM Product", nativeQuery = true)
    List<TotalQuantity> totalQuantity();
}
