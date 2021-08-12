package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.jwt.JwtTokenProvider;
import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Slf4j
@Service
public class AccountsService {
    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserMapper userMapper;
    CustomUserDetailService userDetailService;
//    private final UserRepository userRepository;


    /**
     * 회원가입
     */
    @Transactional
    public int join(User user) {
        //validateDuplicateuser(user); //중복 회원 검증
        //비밀번호 암호화
        accountsMapper.join(user);
        return user.getIdx();
    }

    /**
     *  로그인
     */
    public ReturnUser login(Map<String,String> userIdPassword) {
        ReturnUser returnUser = new ReturnUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(userIdPassword.get("email"));

        try {
            User user = userMapper.findUser(userIdPassword.get("email"))
                    //            User user= userRepository.findbyUsername(logInUser.get("username"))
                    .orElseThrow(() -> new IllegalArgumentException("E-mail를 찾을 수 없습니다."));
            if (!bCryptPasswordEncoder.matches(userIdPassword.get("password"), user.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            returnUser.setToken(jwtTokenProvider.createToken(user.getEmail()));
            returnUser.setName(user.getNickName());
            return returnUser;
        } catch (IllegalArgumentException e) {
            returnUser.setMsg(e.getMessage());
            return returnUser;
        }
    }

    //필요없는 코드지만 재활용가능성 있음.
//    private void validateDuplicateusßer(User user) {
//        userRepository.findByEmail(user.getEmail())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }
}
