package com.example.photologger.photo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;        //idx
    @Column(length = 30, nullable = false, unique = true)
    private String email;   //email
    @Column(length = 15, nullable = false)
    private String password;
    @Column(length = 12, nullable = false)
    private String name;    //이름
    @Column(length = 12, nullable = false)
    private String nicName; //닉네임
    @Column(length = 11, nullable = false, unique = true)
    private String phonenumber; //휴대폰
    @Column(length = 1, nullable = false)
    private int sex;    //성별
    @Column(length = 300, nullable = false)
    private String profile_img_loc;
    @Column(length = 40, nullable = false)
    private String adress;
    @Column(length = 4, nullable = false)
    private String year;
    @Column(length = 2, nullable = false)
    private String month;
    @Column(length = 2, nullable = false)
    private String day;
    //차후 추가바람.
    private int cornfirm;
    private int type;
    private int gall_count;
    private int user_subscribe_count;

}
