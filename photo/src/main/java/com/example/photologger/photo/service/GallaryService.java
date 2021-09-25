package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.GallaryMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class GallaryService {

    @Autowired
    UploaderService uploaderService;

    @Autowired
    GallaryMapper gallaryMapper;

    //갤러리 이미지 저장
    public void gallaryInfoeSave(Gallary gallary, MultipartFile multipartFile)
        throws IOException
    {
        if (multipartFile != null) {
            String path = "gallery";
            gallary.setGallaryImageLocation(uploaderService.upload(multipartFile, path));
            gallary.setGallaryName(multipartFile.getOriginalFilename());
            gallaryMapper.gallaryinfosave(gallary);
            log.info(gallary.toString());
        }
    }

    // 프로필 이미지 저장
    public String profileImagejoin( MultipartFile multipartFile,User user)
        throws IOException
    {
        if (multipartFile != null) {
            String path = "profile";
            user.setProfileImageLocation(uploaderService.upload(multipartFile, path));
            log.info(user.getProfileImageLocation());
            return user.getProfileImageLocation();
        }
        return "fail";
    }

    // 프로필 이미지 저장
   public void profileImageSave( MultipartFile multipartFile,User user)
        throws IOException
    {
        if (multipartFile != null) {
            String path = "profile";
            user.setProfileImageLocation(uploaderService.upload(multipartFile, path));
            log.info(user.getProfileImageLocation());
            gallaryMapper.updateProfileLocation(user.getIdx(), user.getProfileImageLocation(), user.getNickName(),user.getPassword());
        }
    }
}
