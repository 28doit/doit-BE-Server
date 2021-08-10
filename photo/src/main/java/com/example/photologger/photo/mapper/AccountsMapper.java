package com.example.photologger.photo.mapper;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountsMapper {
    void join(User user);
}

