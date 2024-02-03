package com.helphi.exception;

public class ForeignKeyConstraintException extends RuntimeException  {

    public ForeignKeyConstraintException() {
        super();
    }
    public ForeignKeyConstraintException(String message) {
        super(message);
    }
}
