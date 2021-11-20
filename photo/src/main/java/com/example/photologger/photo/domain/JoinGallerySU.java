package com.example.photologger.photo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinGallerySU {

    private Gallery gallery;
    private User user;

}
