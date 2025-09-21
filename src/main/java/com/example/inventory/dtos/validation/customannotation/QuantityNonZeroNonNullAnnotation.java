package com.example.inventory.dtos.validation.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuantityNonZeroValidator.class)
public @interface QuantityNonZeroNonNullAnnotation {
    String message() default "Null or zero are not allowed as quantity while updating";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
