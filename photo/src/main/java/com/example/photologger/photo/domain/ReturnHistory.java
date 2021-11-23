package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ReturnHistory {
//ReturnPaymentHistory
//따로 존재이유 원하는 date방식으로 출력해주기위하여..
    @ApiModelProperty(example = "회원번호")
    int idx;
    @ApiModelProperty(example = "충전포인트")
    int pay;
    @ApiModelProperty(example = "결제카드")
    String payCard;
    @ApiModelProperty(example = "결제시간")
    String time;
    @ApiModelProperty(example = "결제번호")
    String mId;
}

