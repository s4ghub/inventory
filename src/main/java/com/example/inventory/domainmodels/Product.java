package com.example.inventory.domainmodels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "name",unique = true, updatable = false)
    private String name;

    @Column(name = "quantity")
    private Long quantity; //current stock

    @Column(name = "price")
    private String price;

    @Version
    private Long version;
}
