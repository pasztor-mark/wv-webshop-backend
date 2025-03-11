package com.example.webshopbackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        System.out.println("ResponseStatusException buzi");
        ErrorResponse err = new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason()
        );
        return new ResponseEntity<>(err, ex.getStatusCode());

    }
}
