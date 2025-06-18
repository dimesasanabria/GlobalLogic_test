package com.globalogic.test.exception;

public class ExceptionDataNotFound extends RuntimeException {
    public ExceptionDataNotFound(String description){
        super(description);
    }
}
