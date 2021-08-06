package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {


    @Autowired
    UserMapper userMapper;

    //맴버 전체 조회
    public List<User> findOne(int idx){
        return userMapper.findOne(idx);
    }

}
