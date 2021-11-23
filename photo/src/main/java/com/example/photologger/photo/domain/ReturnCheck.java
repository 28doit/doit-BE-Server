package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReturnCheck {
    @ApiModelProperty(example = "email중복체크 msg")
    String msg;
    @ApiModelProperty(example = "email중복체크 bool")
    boolean isValue;
}