package com.globalogic.test.exception;

public class ExceptionUserIsEmpty extends RuntimeException {

    public ExceptionUserIsEmpty(String description){  
        super(description);
    } 
}
