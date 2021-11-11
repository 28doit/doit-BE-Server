package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.service.GallaryService;
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

    private final GallaryService gallaryService;

    @Autowired
    public GalleryController(GallaryService gallaryService){
        this.gallaryService = gallaryService;
    }

    /*갤러리 정보 조회*/
    @GetMapping(value = "/{gallery_id}")
    @ResponseBody
    public Gallary lookUp(
        @PathVariable(value = "gallery_id") Integer galleryId
    ) throws Exception{
        Gallary gallary = gallaryService.gallaryLookUp(galleryId);
        return gallary;
    }
}
