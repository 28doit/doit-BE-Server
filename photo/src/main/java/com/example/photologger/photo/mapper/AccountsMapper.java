package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AccountsMapper {
    public void join(User user);
}

