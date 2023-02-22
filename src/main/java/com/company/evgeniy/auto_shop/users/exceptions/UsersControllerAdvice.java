package com.company.evgeniy.auto_shop.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice("com.company.evgeniy.auto_shop.users")
public class UsersControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArrayList<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ArrayList<String> responseList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            responseList.add(message);
        });
        return new ResponseEntity<>(responseList, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> handleEmailExistsException(EmailExistsException exception) {
        String responseMessage = exception.getMessage();
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
