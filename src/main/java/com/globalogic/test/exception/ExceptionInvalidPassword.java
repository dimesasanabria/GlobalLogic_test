package com.globalogic.test.exception;

public class ExceptionInvalidPassword extends RuntimeException  {  
    
        public ExceptionInvalidPassword(String description){
            super(description);
        }
}
