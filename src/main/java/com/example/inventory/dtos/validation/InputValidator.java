package com.example.inventory.dtos.validation;

import com.example.inventory.dtos.ProductDto;
import com.example.inventory.exceptionhandling.BadInputException;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    //Price should not have more than 2 decimal places
    //Following are not possible 5.345, 7.340, 19.00000
    //Following are possible 5.4, 67, 890.09, 9.20, 2.77
    public void validate(ProductDto dto) {
        String price = dto.getPrice();
        try{
            double priceDouble = Double.parseDouble(price);
        } catch(Exception e) {
            throw new BadInputException("Price should be a numeric quantity of type double");
        }
        int idxDecimalPoint = price.indexOf(".");
        if(idxDecimalPoint != -1) {
            if(price.substring(idxDecimalPoint).length() > 3) {
                throw new BadInputException("Price should not have more than 2 decimal places");
            }
        }
    }
}
