package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Payment {
    @ApiModelProperty(example = "총 포인트")
    private int totalPoint;
    @ApiModelProperty(example = "소비 포인트")
    private int sellPoint;
    @ApiModelProperty(example = "회원번호")
    @NotEmpty
    private int idx;
}
