package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /* Show User One */
    public User findOne(Integer idx) {
        return userMapper.findOne(idx);
    }

    /* Change Password */
    public void UpdatePassword(User user) {
        userMapper.updatePassword(user);
    }

    /* Change NickName */
    public void UpdateName(User user) {
        userMapper.updateName(user);
    }

    /* Find Id*/
    public void findId(String name, String phoneNumber){ userMapper.findId(name, phoneNumber); }

    /* Find Password */
    public User findpwd(User user) {
        return userMapper.findPwd(user);
    }


}
