package com.example.photologger.photo.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryPrice {
    int price;
    int galleryId;
}
