package com.globalogic.test.exception;

public class ExceptionUserAlreadyExists extends  RuntimeException {

    public ExceptionUserAlreadyExists(String Description){
        super(Description);
    }         
}
