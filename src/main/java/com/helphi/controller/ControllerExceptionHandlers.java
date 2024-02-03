package com.helphi.controller;

import jakarta.validation.ConstraintViolationException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandlers {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> resourceNotFoundException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            sb.append(constraintViolation.getMessage());
            sb.append(System.lineSeparator());
        });
        ErrorMessage msg = new ErrorMessage(sb.toString());
        return ResponseEntity.badRequest().body(msg);
    }
}
