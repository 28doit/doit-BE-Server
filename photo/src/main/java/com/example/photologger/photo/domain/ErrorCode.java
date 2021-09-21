package com.example.photologger.photo.domain;

public enum ErrorCode {
    NULL_POINT_EXCEPTION(400,"C001","NULL_POINT_EXCEPTION");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status,final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
