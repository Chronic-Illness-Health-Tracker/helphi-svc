package com.helphi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEntityException extends RuntimeException {
    private String details;


    public DuplicateEntityException() {
        super();
    }
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, String details) {
        super(message);
        this.details = details;
    }
}
