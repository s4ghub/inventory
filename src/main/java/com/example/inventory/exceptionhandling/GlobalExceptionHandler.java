package com.example.inventory.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  Form validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ErrorCodes.ERRORCODE4, ErrorCodes.ERRORCODE4MESSAGE);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = new ErrorResponse(ErrorCodes.ERRORCODE4, "Validation failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     *  In case the input Json is not valid (For example enclosing curly braces don't match
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ErrorCodes.ERRORCODE2, ErrorCodes.ERRORCODE2MESSAGE);
        ErrorResponse response = new ErrorResponse(ErrorCodes.ERRORCODE2, null, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom Input exception which is specific to the application
     */
    @ExceptionHandler(BadInputException.class)
    public ResponseEntity<ErrorResponse> handleBadInputException(BadInputException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ErrorCodes.ERRORCODE1, ErrorCodes.ERRORCODE1MESSAGE);
        ErrorResponse response = new ErrorResponse(ErrorCodes.ERRORCODE1, ex.getMessage(), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom Input exception which is specific to the application
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ErrorCodes.ERRORCODE5, ErrorCodes.ERRORCODE5MESSAGE);
        ErrorResponse response = new ErrorResponse(ErrorCodes.ERRORCODE5, ex.getMessage(), errors);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * For General purpose Exception handler in case all other above handlers do not catch.
     * @param ex exception
     * @return ResponseEntity
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ErrorCodes.ERRORCODE3, ErrorCodes.ERRORCODE3MESSAGE);
        ErrorResponse response = new ErrorResponse(ErrorCodes.ERRORCODE3, ex.getMessage(), errors);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
