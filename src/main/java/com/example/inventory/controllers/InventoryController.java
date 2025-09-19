package com.example.inventory.controllers;

import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.validation.InputValidatior;
import com.example.inventory.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class InventoryController {
    private final InventoryService inventoryService;
    private final InputValidatior inputValidator;

    @Autowired
    public InventoryController(InventoryService inventoryService, InputValidatior inputValidator) {
        this.inventoryService = inventoryService;
        this.inputValidator = inputValidator;
    }

    /**
     * This endpoint inserts the record for a Product for the first time
     */
    //TODO: Duplicate name proper error to provide
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto dto) {
        inputValidator.validate(dto);
        return new ResponseEntity<>(inventoryService.createProduct(dto), HttpStatus.CREATED);
    }

    /**
     * This endpoint searches the record for a Product, when a name is provided
     */
    @Operation(summary = "search a Product by name", description = "When name is supplied as quaryparameter, The record for the product is found")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("search")
    public ResponseEntity<ProductDto> searchProductByName( @RequestParam(value = "name") String name) {
        return new ResponseEntity<>(inventoryService.searchProductByName(name), HttpStatus.OK);
    }
}
