package com.helphi.controller.error;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.ErrorMessage;

@Getter
@Setter
public class HelphiErrorMessage extends ErrorMessage {

    private String details;
    public HelphiErrorMessage(String message) {
        super(message);
    }

    public HelphiErrorMessage(String message, String details) {
        super(message);
        this.details = details;
    }
}
