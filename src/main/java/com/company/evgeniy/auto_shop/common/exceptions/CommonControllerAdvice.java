package com.company.evgeniy.auto_shop.common.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ArrayList<String>> handleConstraintViolationException(ConstraintViolationException exception) {
        ArrayList<String> response = new ArrayList<>();
        exception.getConstraintViolations().forEach(value -> {
            String message = value.getMessage();
            response.add(message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
