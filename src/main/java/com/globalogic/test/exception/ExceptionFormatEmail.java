package com.globalogic.test.exception;

public class ExceptionFormatEmail extends RuntimeException {
    public ExceptionFormatEmail(String description){
        super(description);
    }
}
