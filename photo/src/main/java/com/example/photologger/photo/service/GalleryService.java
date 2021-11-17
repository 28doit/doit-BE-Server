package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.GalleryMapper;
import com.example.photologger.photo.mapper.UserMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class GalleryService {

    private final UploaderService uploaderService;
    private final GalleryMapper galleryMapper;
    private final UserMapper userMapper;

    public GalleryService(UploaderService uploaderService,
        GalleryMapper galleryMapper, UserMapper userMapper) {
        this.uploaderService = uploaderService;
        this.galleryMapper = galleryMapper;
        this.userMapper = userMapper;
    }


    //갤러리 이미지 저장
    public void galleryInfoeSave(Gallery gallery, MultipartFile multipartFile)
        throws IOException {
        if (multipartFile != null) {
            String path = "gallery";
            gallery.setGalleryImageLocation(uploaderService.upload(multipartFile, path));
            gallery.setGalleryName(multipartFile.getOriginalFilename());
            galleryMapper.galleryinfosave(gallery);
            log.info(gallery.toString());
        }
    }

    // 프로필 이미지 저장
    public String profileImagejoin(MultipartFile multipartFile, User user)
        throws IOException {
        if (multipartFile != null) {
            String path = "profile";
            user.setProfileImageLocation(uploaderService.upload(multipartFile, path));
            log.info(user.getProfileImageLocation());
            return user.getProfileImageLocation();
        }
        return "fail";
    }

    // 프로필 이미지 저장
    public void profileImageSave(MultipartFile multipartFile, User user)
        throws IOException {
        if (multipartFile != null) {
            String path = "profile";
            user.setProfileImageLocation(uploaderService.upload(multipartFile, path));
            log.info(user.getProfileImageLocation());
            galleryMapper.updateProfileLocation(user.getIdx(), user.getProfileImageLocation(),
                user.getNickName(), user.getPassword());
        }
    }

    //갤러리 조회
    public Gallery galleryLookUp(Integer galleryId) {
        galleryMapper.galleryViewCount(galleryUser(galleryId));
        return galleryMapper.galleryLookUp(galleryId);
    }

    //갤러리 아이디를 이용해 겔러리 정보 불러오기
    Integer galleryUser(Integer gallery){
        Gallery galleryInfo = galleryMapper.galleryLookUp(gallery);
        Integer galleryId = galleryInfo.getGalleryId();
        return galleryId;
    }
}
