package com.example.photologger.photo.mapper;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //특정 회원 정보 불러오기
    List<User> findOne(@Param("idx") Integer idx);

    //비밀번호 변경
    void updatePassword(User user);

    //비밀번호 찾기
    void findPw(User user);
}
