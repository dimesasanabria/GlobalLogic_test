package com.globalogic.test.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionList {
private List <ResponseError> error = new ArrayList<>();

    public ExceptionList() {;
    }

    public List<ResponseError> getErrors() {
        return error;
    }

    public void setErrors(List<ResponseError> errors) {
        this.error = errors;
    }

    public void addError(ResponseError responseError) {
        this.error.add(responseError);
    }
}
