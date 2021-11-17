package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GalleryMapper {

    //갤러리 정보 저장
    void galleryinfosave(Gallery gallery);

    void updateProfileLocation(Integer idx, String profileImageLocation, String nickName, String password);

    Gallery galleryLookUp(@Param("galleryId")Integer galleryId);

    void galleryViewCount(Integer galleryId);

}
