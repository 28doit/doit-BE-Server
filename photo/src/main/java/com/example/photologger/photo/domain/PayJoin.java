package com.example.photologger.photo.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayJoin {
    String orderNumber;
    Date date;
    int idx;
    int gallary_Id;
}
