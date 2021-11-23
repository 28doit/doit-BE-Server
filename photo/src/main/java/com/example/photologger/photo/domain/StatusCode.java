package com.example.photologger.photo.domain;

public enum StatusCode {
    OK("CM0", "정상작동")
    ,ERR_PAGINATION("CM1", "잘못된 인덱스 접근")
    ;

    private final String code;
    private final String message;

    StatusCode(
        final String code,
        final String message
    ){
        this.code =code;
        this.message=message;
    }
}
