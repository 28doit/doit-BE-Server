package com.example.photologger.photo.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReturnWithdrawal {
    int idx;
    String withdrawalNumber;
    int point;
    String date;
}
