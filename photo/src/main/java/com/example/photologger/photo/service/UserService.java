package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.repository.DBUserRepository;



public class UserService {

    private final DBUserRepository userRepository = new DBUserRepository();
    /**
     * 회원가입
     */
    public int join(User user)
    {
        //같은 email 중복회원 X
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u->{
            throw new IllegalStateException("이미 가입되있는 Email입니다.");
        });
        userRepository.save(user);
        return user.getIdx();
    }
}
