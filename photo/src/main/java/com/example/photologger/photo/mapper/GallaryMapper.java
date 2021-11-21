package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GallaryMapper {

    //갤러리 정보 저장
    void gallaryinfosave(Gallery gallary);

}
