package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
