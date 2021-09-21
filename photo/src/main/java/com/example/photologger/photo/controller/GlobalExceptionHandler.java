package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.ErrorCode;
import com.example.photologger.photo.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> NullPointerException(NullPointerException e){
        log.error("NullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NULL_POINT_EXCEPTION, e.getMessage());
    }

}
