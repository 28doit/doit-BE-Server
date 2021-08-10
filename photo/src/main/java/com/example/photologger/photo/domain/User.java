package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @ApiModelProperty(example = "회원 번호")
    private Integer idx;        //idx

    @ApiModelProperty(example = "E-mail")
    private String email;   //email

    @ApiModelProperty(example = "비밀번호")
    private String password;

    @ApiModelProperty(example = "이름")
    private String userName;    //이름
    @ApiModelProperty(example = "닉네임")
    private String nickName; //닉네임
    @ApiModelProperty(example = "전화번호")
    private String phoneNumber; //휴대폰
    @ApiModelProperty(example = "성별")
    private int sex;    //성별
    @ApiModelProperty(example = "프로필 사진")
    private String profileImageLocation; // 이미지 위치
    @ApiModelProperty(example = "년")
    private String userYear; // 년도
    @ApiModelProperty(example = "월")
    private String userMonth; // 월
    @ApiModelProperty(example = "일")
    private String userDay; // 일
    @ApiModelProperty(example = "사용자 직급")
    private int type; //사용자 직급
    @ApiModelProperty(example = "사진 갯수")
    private int gallaryCount; // 사진 갯수
    @ApiModelProperty(example = "구독자 수")
    private int userSubscribeCount; // 구독자 수
}
