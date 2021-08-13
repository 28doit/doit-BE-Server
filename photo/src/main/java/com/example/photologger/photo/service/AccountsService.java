package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.AccountsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    @Autowired
    AccountsMapper accountsMapper;

    /**
     * 회원가입
     */
    public int join(User user) {
        accountsMapper.join(user);
        return user.getIdx();
    }
}
