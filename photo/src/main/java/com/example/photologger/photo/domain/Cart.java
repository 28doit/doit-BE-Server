package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @ApiModelProperty(example = "회원 번호")
    int idx;
    @ApiModelProperty(example = "사진 번호")
    int galleryId;
}
