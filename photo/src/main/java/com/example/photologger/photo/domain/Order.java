package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    @ApiModelProperty(example = "회원 번호")
    int idx;
    @ApiModelProperty(example = "주문 번호")
    String orderNumber;
    @ApiModelProperty(example = "주문 날짜 시간")
    Date date;
    @ApiModelProperty(example = "사진 번호")
    int galleryId;
}
