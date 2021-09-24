package com.example.photologger.photo.domain;

import lombok.*;
import org.joda.time.DateTime;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PaymentHistory
{
    int idx;
    int pay;
    String name;
    String email;
    String payCard;
    Date time;
    String mId;

}
