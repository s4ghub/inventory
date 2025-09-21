package com.example.inventory.dtos.validation.customannotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityNonZeroValidator implements ConstraintValidator<QuantityNonZeroNonNullAnnotation, Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        if(aLong == null || aLong == 0l) {
            return false;
        }
        return true;
    }
}
