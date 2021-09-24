package com.example.photologger.photo.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReturnUser {
    private String token;
    private String name;
    private int isValue;
}
