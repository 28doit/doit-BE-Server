package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.domain.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GallaryMapper {

    //갤러리 정보 저장
    void gallaryinfosave(Gallary gallary);

    void updateProfileLocation(Integer idx, String profileImageLocation, String nickName, String password);

    Gallary gallaryLookUp(@Param("gallaryId")Integer gallaryId);

    void galleryViewCount(Integer gallaryId);

}
