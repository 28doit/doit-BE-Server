package com.example.photologger.photo.mapper;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findOne(@Param("idx") Integer idx);

    void updatePassword(User user);
}
