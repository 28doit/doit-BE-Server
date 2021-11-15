package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Gallery {
    @ApiModelProperty(example = "사진 번호")
    private int galleryId;

    @ApiModelProperty(example = "회원 번호")
    private  int idx;

    @ApiModelProperty(example = "사진 주소")
    private String galleryAdress;

    @ApiModelProperty(example = "사진 시간")
    private String galleryTime;

    @ApiModelProperty(example = "시진 계절")
    private int gallerySeseon;

    @ApiModelProperty(example = "사진 방향")
    private String galleryDireaction;

    @ApiModelProperty(example = "사진 이름")
    private String galleryName;

    @ApiModelProperty(example = "사진 업로드 위치")
    private String galleryImageLocation;

    @ApiModelProperty(example = "사진 구독자 수")
    private int gallerySubscribeCount;

    private String galleryDireaction_Y;
    private int galleryViews;
}
