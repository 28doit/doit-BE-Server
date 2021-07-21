package com.example.photologger.photo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {


    private int Idx;        //idx

    private String Email;   //email

    private String Password;

    private String Name;    //이름

    private String NicName; //닉네임
    private String Phonenumber; //휴대폰

    private int Sex;    //성별

    private String Profile_img_loc;

    private String Adress;

    private String Year;

    private String Month;

    private String Day;
    //차후 추가바람.
    private int Cornfirm;
    private int Type;
    private int Gall_count;
    private int User_subscribe_count;

}
