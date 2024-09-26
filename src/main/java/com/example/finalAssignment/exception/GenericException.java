package com.example.finalAssignment.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class GenericException extends RuntimeException {
    public Integer statusCode;

    public GenericException(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }

}
