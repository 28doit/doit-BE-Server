package com.example.photologger.photo.domain;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ReturnHistory {

    int idx;
    int pay;
    String name;
    String email;
    String payCard;
    String time;
    String mId;

}

