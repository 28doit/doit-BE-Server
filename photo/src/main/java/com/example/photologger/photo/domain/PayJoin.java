package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayJoin {
    @ApiModelProperty(example = "주문번호")
    String orderNumber;
    @ApiModelProperty(example = "날짜")
    Date date;
    @ApiModelProperty(example = "회원 번호")
    int idx;
    @ApiModelProperty(example = "사진 번호")
    int gallary_Id;
}
