package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {


    @Autowired
    UserMapper userMapper;

    //맴버 조회
    public User findOne(Integer idx) {
        return userMapper.findOne(idx);
    }

    //비밀번호 변경
    public void UpdatePassword(User user) {
        userMapper.updatePassword(user);
    }

    //닉네임 변경
    public void UpdateName(User user) {
        userMapper.updateName(user);
    }


    public User findpwd(User user) {
        return userMapper.findPwd(user);
    }


}
