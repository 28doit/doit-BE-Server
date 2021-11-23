package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinGalleryUserLU {

   private Gallery gallery;
    private User user;
    private GalleryPrice galleryPrice;


}
