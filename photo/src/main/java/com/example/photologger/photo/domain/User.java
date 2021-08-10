package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @ApiModelProperty(example = "회원 번호")
    private Integer idx;

    @ApiModelProperty(example = "프로필 사진")
    private String profileImageLocation;

    @ApiModelProperty(example = "E-mail")
    private String email;

    @ApiModelProperty(example = "이름")
    private String name;

    @ApiModelProperty(example = "닉네임")
    private String nickName;

    @ApiModelProperty(example = "전화번호")
    private String phoneNumber;

    @ApiModelProperty(example = "성별")
    private int sex;

    @ApiModelProperty(example = "년")
    private String userYear;

    @ApiModelProperty(example = "월")
    private String userMonth;

    @ApiModelProperty(example = "일")
    private String userDay;

    @ApiModelProperty(example = "비밀번호")
    private String password;

    @ApiModelProperty(example = "사용자 직급")
    private int type;

    @ApiModelProperty(example = "사진 갯수")
    private int gallaryCount;

    @ApiModelProperty(example = "구독자 수")
    private int userSubscribeCount;
}
