package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.joda.time.DateTime;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PaymentHistory
{
    @NotEmpty
    @ApiModelProperty(example = "회원번호")
    int idx;
    @ApiModelProperty(example = "충전포인트 내역")
    int pay;
    @ApiModelProperty(example = "결제카드")
    String payCard;
    @ApiModelProperty(example = "결제시간")
    Date time;
    @ApiModelProperty(example = "결제번호")
    String mId;

}
