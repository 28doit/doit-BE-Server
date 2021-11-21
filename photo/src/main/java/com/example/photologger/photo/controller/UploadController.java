package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.service.GallaryService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/uploader")
public class UploadController {

//    @Autowired
//    private GallaryService gallaryService;
//
//    @PostMapping(value = "/images")
//    public String upload(
//        Gallery gallary,
//        @RequestParam(value = "images") MultipartFile multipartFile
//    ) throws IOException {
//        gallaryService.gallaryInfoeSave(gallary ,multipartFile);
//        return "ok";
//    }

}
