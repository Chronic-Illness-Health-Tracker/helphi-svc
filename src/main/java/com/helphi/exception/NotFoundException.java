package com.helphi.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private String details;

    public NotFoundException() {
        super();
    }
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }
}
