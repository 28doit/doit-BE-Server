package com.example.photologger.photo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {


    private int idx;        //idx

    private String email;   //email

    private String password;

    private String name;    //이름

    private String nicName; //닉네임
    private String phonenumber; //휴대폰

    private int sex;    //성별

    private String profile_img_loc;

    private String adress;

    private String year;

    private String month;

    private String day;
    //차후 추가바람.
    private int cornfirm;
    private int type;
    private int gall_count;
    private int user_subscribe_count;

}
