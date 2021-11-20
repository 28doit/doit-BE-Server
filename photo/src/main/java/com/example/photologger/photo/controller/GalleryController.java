package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.JoinGalleryUserLU;
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

    /* Gallery Info LookUp */
    @GetMapping(value = "/{gallery_id}")
    @ResponseBody
    public JoinGalleryUserLU lookUp(
        @PathVariable(value = "gallery_id") Integer galleryId
    ) throws Exception{
        Gallery gallery1 = galleryService.galleryLookUp(galleryId);
        return galleryService.galleryAndUserLU(gallery1.getGalleryId(), gallery1.getIdx());
    }
}
