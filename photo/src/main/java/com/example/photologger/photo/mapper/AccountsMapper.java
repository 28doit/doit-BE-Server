package com.example.photologger.photo.mapper;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AccountsMapper {

    void join(User user);

    void emailCheck(String email);

    //토큰발급용 맵퍼jwt
    Optional<User> findEmail(String email);
}

