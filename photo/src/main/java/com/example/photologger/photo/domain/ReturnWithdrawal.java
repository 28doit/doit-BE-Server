package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReturnWithdrawal {
    @ApiModelProperty(example = "회원번호")
    int idx;
    @ApiModelProperty(example = "수익번호")
    String withdrawalNumber;
    @ApiModelProperty(example = "가격")
    int point;
    @ApiModelProperty(example = "날짜")
    String date;
}
