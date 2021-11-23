package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.ReturnPhotosForSale;
import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /* Show User One */
    User findOne(@Param("idx") Integer idx);

    /* Change Password */
    void updatePassword(User user);

    /* Change NickName */
    void updateName(User user);

    /* Find Id*/
    void findId(String name, String phoneNumber);

    /* Find Password */
    User findPwd(User user);

    //구독중인사람(user1를 구독하고있는사람의 목록을 보여준다)
    List<ReturnUser> subScribe(int SubScribeIdx);

    //사람이 등록한 사진은 반환한다.
    List<ReturnPhotosForSale> photosForSale(int idx);

}
