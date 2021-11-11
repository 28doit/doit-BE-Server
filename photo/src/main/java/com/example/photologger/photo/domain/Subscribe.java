package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscribe {

    @ApiModelProperty(example = "유저번호")
    private int idx;

    @ApiModelProperty(example = "사진 번호")
    private int galleryId;

    @ApiModelProperty(example = "구독할 유저 번호")
    private int subscribeUser;
}
