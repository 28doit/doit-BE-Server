package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Gallary {
    @ApiModelProperty(example = "사진 번호")
    private int gallaryId;

    @ApiModelProperty(example = "회원 번호")
    private  int idx;

    @ApiModelProperty(example = "사진 주소")
    private String gallaryAdress;

    @ApiModelProperty(example = "사진 시간")
    private String gallaryTime;

    @ApiModelProperty(example = "시진 계절")
    private int gallarySeseon;

    @ApiModelProperty(example = "사진 좌표 X")
    private String gallaryDireactionX;

    @ApiModelProperty(example = "사진 좌표 Y")
    private String gallaryDireactionY;

    @ApiModelProperty(example = "사진 이름")
    private String gallaryName;

    @ApiModelProperty(example = "사진 업로드 위치")
    private String gallaryImageLocation;

    @ApiModelProperty(example = "사진 구독자 수")
    private int gallarySubscribeCount;

}
