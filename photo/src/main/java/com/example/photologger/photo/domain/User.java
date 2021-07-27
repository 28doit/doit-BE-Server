package com.example.photologger.photo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private int idx;        //idx
    private String email;   //email
    private String password;
    private String userName;    //이름
    private String nickName; //닉네임
    private String phoneNumber; //휴대폰
    private int sex;    //성별
    private String profileImageLocation; // 이미지 위치
    private String userYear; // 년도
    private String userMonth; // 월
    private String userDay; // 일
    private int type; //사용자 직급
    private int gallaryCount; // 사진 갯수
    private int userSubscribeCount; // 구독자 수
}
