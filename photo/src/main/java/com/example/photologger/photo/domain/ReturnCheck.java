package com.example.photologger.photo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReturnCheck {
    String msg;
    boolean isValue;
}