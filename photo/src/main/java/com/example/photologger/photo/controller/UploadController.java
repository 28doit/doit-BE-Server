package com.example.photologger.photo.controller;

import com.example.photologger.photo.service.UploaderService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploader")
public class UploadController {

    @Autowired
    private UploaderService uploaderService;

    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        uploaderService.upload(multipartFile, "static");
        return "test";
    }

}
