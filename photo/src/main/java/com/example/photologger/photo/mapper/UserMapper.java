package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.ReturnPhotosForSale;
import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //특정 회원 정보 불러오기
    User findOne(@Param("idx") Integer idx);

    //비밀번호 변경
    void updatePassword(User user);

    //닉네임 변경
    void updateName(User user);

    //비밀번호 찾기
    User findPwd(User user);

    //구독중인사람(user1를 구독하고있는사람의 목록을 보여준다)
    List<ReturnUser> subScribe(int SubScribeIdx);

    //사람이 등록한 사진은 반환한다.
    List<ReturnPhotosForSale> photosForSale(int idx);

}
