package com.globalogic.test.exception;

import java.sql.Timestamp;

public class ResponseError {
    private String detail;
    private Timestamp timeslamp;
    private int code;

    public ResponseError(String message, int code) {
        this.detail = message;
        this.timeslamp = new Timestamp(System.currentTimeMillis());
        this.code = code; // Default error code

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String message) {
        this.detail = message;
    }

    public Timestamp getTimeslamp() {
        return timeslamp;
    }

    public void setTimeslamp(Timestamp timeslamp) {
        this.timeslamp = timeslamp;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
