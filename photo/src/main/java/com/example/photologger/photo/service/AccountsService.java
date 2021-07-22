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
    public int join(User user) throws Exception {
        //validateDuplicateuser(user); //중복 회원 검증
        accountsMapper.join(user);
        return user.getIdx();
    }
//    private void validateDuplicateuser(User user) {
//        userRepository.findByEmail(user.getEmail())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }
}
