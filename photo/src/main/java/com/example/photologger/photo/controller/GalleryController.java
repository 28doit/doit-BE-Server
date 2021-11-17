package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.service.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/gallery")
@RestController
public class GalleryController {

    private final GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService){
        this.galleryService = galleryService;
    }

    /*갤러리 정보 조회*/
    @GetMapping(value = "/{gallery_id}")
    @ResponseBody
    public Gallery lookUp(
        @PathVariable(value = "gallery_id") Integer galleryId
    ) throws Exception{
        Gallery gallary = galleryService.galleryLookUp(galleryId);
        return gallary;
    }
}
