package com.example.finalAssignment.exception;

import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException ex){
        ErrorObj errorObj = new ErrorObj(ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(errorObj, HttpStatus.BAD_REQUEST);
    }
}
