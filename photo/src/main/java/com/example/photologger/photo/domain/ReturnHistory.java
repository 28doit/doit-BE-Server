package com.example.photologger.photo.domain;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ReturnHistory {
//ReturnPaymentHistory
//따로 존재이유 원하는 date방식으로 출력해주기위하여..
    int idx;
    int pay;
    String payCard;
    String time;
    String mId;

}

