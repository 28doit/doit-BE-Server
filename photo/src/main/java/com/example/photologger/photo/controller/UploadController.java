package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.GalleryService;
import com.example.photologger.photo.service.UserService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/uploader")
public class UploadController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private UserService userService;

    //갤러리 이미지 업로드
    @PostMapping(value = "/images")
    public String upload(
        Gallery gallery,
        @RequestParam(value = "images") MultipartFile multipartFile
    ) throws IOException {
        galleryService.galleryInfoeSave(gallery ,multipartFile);
        return "ok";
    }

    //프로필 이미지 업로드
    @PostMapping(value = "/profileimage")
    public String profileupload(
        @RequestParam(value = "idx") Integer idx,
        @RequestParam(value = "nickName") String nickname,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "images") MultipartFile multipartFile
    ) throws IOException {
        User user = userService.findOne(idx);
        user.setNickName(nickname);
        user.setPassword(password);

        galleryService.profileImageSave(multipartFile, user);
        return "ok";
    }


}
