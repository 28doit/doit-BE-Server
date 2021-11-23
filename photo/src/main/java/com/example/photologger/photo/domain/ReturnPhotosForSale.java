package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnPhotosForSale {
    @ApiModelProperty(example = "회원번호")
    int idx;
    @ApiModelProperty(example = "사진번호")
    int galleryId;
    @ApiModelProperty(example = "사진")
    String galleryImageLocation;
}
