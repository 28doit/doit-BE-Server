package com.example.photologger.photo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private int Idx;        //idx
    private String Email;   //email
    private String Password;
    private String U_name;    //이름
    private String NicName; //닉네임
    private String phoneNumber; //휴대폰
    private int Sex;    //성별
    private String profileImageLocation; // 이미지 위치
    private String U_year; // 년도
    private String U_month; // 월
    private String U_day; // 일
    private int Cornfirm; // 약관 동의 유무
    private int Type; //사용자 직급
    private int Gall_count; // 사진 갯수
    private int User_subscribe_count; // 구독자 수
}
