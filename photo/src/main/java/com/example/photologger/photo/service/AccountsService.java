package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.ReturnCheck;
import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.jwt.JwtTokenProvider;
import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.ls.LSException;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

@Slf4j
@Service
public class AccountsService {
    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserMapper userMapper;
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

        log.info(userIdPassword.get("email"));
        log.info(userIdPassword.get("password"));

        try {
            User user = accountsMapper.findEmail(userIdPassword.get("email"))
                    //            User user= userRepository.findbyUsername(logInUser.get("username"))
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));
            if (!bCryptPasswordEncoder.matches(userIdPassword.get("password"), user.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            returnUser.setToken(jwtTokenProvider.createToken(user.getEmail()));
            log.info(returnUser.getToken());
            returnUser.setName(user.getEmail());
            return returnUser;
        } catch (IllegalArgumentException e) {
            returnUser.setMsg(e.getMessage());
            return returnUser;
        }
    }
    public Object email_Check(String email)
    {
        ReturnCheck returncheck = new ReturnCheck();
        if(email.equals(""))
        {
            returncheck.setMsg("아무것도 입력하지 않으셨습니다.");
            returncheck.setIsvalue(false);
            log.info(returncheck.toString());
            return returncheck;
        }
        try {
                accountsMapper.findEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("가입가능한 email 입니다."));
                 returncheck.setMsg("이미 회원가입된 email 입니다.");
                 returncheck.setIsvalue(true);
                 log.info(returncheck.toString());
        }catch (IllegalArgumentException e) {
            returncheck.setMsg(e.getMessage());
            returncheck.setIsvalue(false);
            log.info(returncheck.toString());
            return returncheck;
        }
        return returncheck;
    }
    public HashMap token_Expiration(String token,String email)
    {
        HashMap<String,Boolean> TrueAndFlase = new HashMap();
        try {

            if (jwtTokenProvider.getUserPk(token).equals(email))
            {
                User user = accountsMapper.findEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("토큰에 정보와 일치하지 않습니다."));

                if (jwtTokenProvider.validateToken(token))
                {
                    log.info(user.toString());
                    HashMap<String,Object> tmp = new HashMap<>();
                    tmp.put("Name",user.getName());
                    tmp.put("Email",user.getEmail());
                    tmp.put("Year",user.getUserYear());
                    tmp.put("Month",user.getUserMonth());
                    tmp.put("Day",user.getUserDay());
                    tmp.put("Sex",user.getSex());
                    tmp.put("PhoneNumber",user.getPhone_Number());
                    log.info("현재 사용가능한 토큰입니다. "+ "토큰 만료시간 : {} ");//수정하세요
                    return tmp;
                }
                //사실상 동작안하는부분(0.0몇초차이로 할수도)
                TrueAndFlase.put("Token", jwtTokenProvider.validateToken(token));
                return TrueAndFlase;
            }
        }catch (ExpiredJwtException e) {
            TrueAndFlase.put("Token", jwtTokenProvider.validateToken(token));
            log.info("해당 토큰은 이미 만료된 토큰입니다.   " + "토큰 만료시간 : {} " + e.getClaims().getExpiration());   //현재 디버그 동작안함 수정 필요
            return TrueAndFlase;
        }
        //사실상 동작안하는부분
        TrueAndFlase.put("Token",jwtTokenProvider.validateToken(token));
        return TrueAndFlase;
    }

    //필요없는 코드지만 재활용가능성 있음.
//    private void validateDuplicateusßer(User user) {
//        userRepository.findByEmail(user.getEmail())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }
}
