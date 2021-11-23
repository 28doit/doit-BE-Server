package com.example.photologger.photo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JoinGalleryAndSubscribe {

    private Gallery gallery;
    private User user;

}
