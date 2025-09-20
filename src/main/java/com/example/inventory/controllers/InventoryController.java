package com.example.inventory.controllers;

import com.example.inventory.dtos.ProductDto;
import com.example.inventory.dtos.QuantityDto;
import com.example.inventory.dtos.validation.InputValidatior;
import com.example.inventory.pagination.PaginationRequest;
import com.example.inventory.pagination.PagingResult;
import com.example.inventory.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "create a new product", description = "With proper input a new product is created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Wrong user input")
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto dto) {
        inputValidator.validate(dto);
        return new ResponseEntity<>(inventoryService.createProduct(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagingResult<ProductDto>> listAllProducts(@RequestParam(required = false) Integer page,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) String sortField,
                                                                    @RequestParam(required = false) Sort.Direction direction) {
        final PaginationRequest request = new PaginationRequest(page, size, sortField, direction);
        return new ResponseEntity<>(inventoryService.getAllProducts(request), HttpStatus.OK);
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

    @Operation(summary = "Update the quantity", description = "When id and quantity are supplied, The quantity of the product is updated to the currently available quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PutMapping("/{id}/quantity")
    public ResponseEntity<?> updateProductQuantity(@Valid @RequestBody QuantityDto dto, @PathVariable("id") long id) {
        inventoryService.updateTheQuantityOfAProduct(dto, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "delete the quantity", description = "When id is supplied, The product record is deleted from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted")
    })
    @DeleteMapping("/{id}")
    //@ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        inventoryService.deleteAProductById(id);
        return ResponseEntity.noContent().build();
    }
}
