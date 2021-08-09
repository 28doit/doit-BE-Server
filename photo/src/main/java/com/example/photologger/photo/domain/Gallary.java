package com.example.photologger.photo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Gallary {

    private int gallaryId;
    private String gallaryAddress;
    private String time;

}
