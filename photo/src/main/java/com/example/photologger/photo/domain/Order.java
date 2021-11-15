package com.example.photologger.photo.domain;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    int idx;
    String orderNumber;
    Date date;
    int galleryId;
}
