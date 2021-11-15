package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReturnUser {
    @ApiModelProperty(example = "JWT")
    private String token;

    @ApiModelProperty(example = "유저이름")
    private String email;

    @ApiModelProperty(example = "0 1 2 구분")
    private int isValue;
}
