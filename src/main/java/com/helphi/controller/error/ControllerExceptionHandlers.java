package com.helphi.controller.error;

import com.helphi.exception.DuplicateEntityException;
import com.helphi.exception.ForeignKeyConstraintException;
import com.helphi.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandlers {
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> resourceNotFoundException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            sb.append(constraintViolation.getMessage());
            sb.append(System.lineSeparator());
        });
        HelphiErrorMessage msg = new HelphiErrorMessage(sb.toString());
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler(value = { ForeignKeyConstraintException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> foreignKeyConstraintException(ForeignKeyConstraintException ex) {
        HelphiErrorMessage msg = new HelphiErrorMessage(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        HelphiErrorMessage msg = new HelphiErrorMessage(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler(value = { DuplicateEntityException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> notFoundException(DuplicateEntityException ex) {
        HelphiErrorMessage msg = new HelphiErrorMessage(ex.getLocalizedMessage(), ex.getDetails());
        return ResponseEntity.badRequest().body(msg);
    }

/*    @ExceptionHandler(value = { Throwable.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> genericException(Throwable ex) {
        ErrorMessage msg = new ErrorMessage("Could not complete request");
        return ResponseEntity.badRequest().body(msg);
    }*/
}
