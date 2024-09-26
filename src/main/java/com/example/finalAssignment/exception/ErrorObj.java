package com.example.finalAssignment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorObj {
    private String message;
    private Integer statusCode;
}
