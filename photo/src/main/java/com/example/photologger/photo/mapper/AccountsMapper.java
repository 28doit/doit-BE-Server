package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountsMapper {
    void join(User user);
}

