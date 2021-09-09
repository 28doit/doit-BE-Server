package com.example.photologger.photo.domain;

import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final T data;
    private StatusCode statusCode;

    public static class ResponseDtoBuilder<T>{
        private T data;
        private StatusCode statusCode;

        public ResponseDtoBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDtoBuilder<T> statusCode(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<T>(this);
        }
    }

    private ResponseDto(ResponseDtoBuilder<T> responseDtoBuilder) {
        this.data = responseDtoBuilder.data;
        this.statusCode = responseDtoBuilder.statusCode;
    }
    public static <T> ResponseDtoBuilder<T> builder() {
        return new ResponseDtoBuilder<>();
    }
}
