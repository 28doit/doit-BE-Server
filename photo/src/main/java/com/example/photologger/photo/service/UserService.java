package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository= userRepository;
    }
    /**
     * 회원가입
     */
    public int join(User member) {
        validateDuplicateMember(member); //중복 회원 검증
        userRepository.save(member);
        return member.getIdx();
    }
    private void validateDuplicateMember(User member) {
        userRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
