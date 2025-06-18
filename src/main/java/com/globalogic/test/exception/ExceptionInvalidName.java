package com.globalogic.test.exception;

public class ExceptionInvalidName extends RuntimeException {
    public ExceptionInvalidName(String description){
        super(description);
    }
}
