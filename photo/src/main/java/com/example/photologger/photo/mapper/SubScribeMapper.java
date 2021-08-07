package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubScribeMapper {

    void UserSubScribe(User user);
    void GallarySubSubScribe(User user);
}
