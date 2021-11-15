package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReturnGalleryHistory {
    @ApiModelProperty(example = "사진 번호")
    private int galleryId;

    @ApiModelProperty(example = "사진 이름")
    private String galleryName;

    @ApiModelProperty(example = "사진 업로드 위치")
    private String galleryImageLocation;

    @ApiModelProperty(example = "결제 날짜")
    private String date;

    @ApiModelProperty(example = "주문 번호")
    private String order_number;

    @ApiModelProperty(example = "작가이름")
    private String seller_Name;
}
