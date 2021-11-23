package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryPrice {
    @ApiModelProperty(example = "사진 가격")
    int price;
    @ApiModelProperty(example = "사진 번호")
    int galleryId;
}
