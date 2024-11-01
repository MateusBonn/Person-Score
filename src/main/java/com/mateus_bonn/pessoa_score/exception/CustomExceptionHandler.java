package com.mateus_bonn.pessoa_score.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.mateus_bonn.pessoa_scrore")
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Error> handleException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getError());
    }

}
