package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    UserMapper userMapper;
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "";

    //맴버 전체 조회
    public List<User> findOne(Integer idx) {
        return userMapper.findOne(idx);
    }

    //비밀번호 변경
    public void UpdatePassword(User user) {
        userMapper.updatePassword(user);
    }

    //비밀번호 찾기
    public void findpwd(User user) {
        userMapper.findPw(user);
    }

    //비밀번호 찾기 이메일 보내기
    public void sendMail(String pwd, String address){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setFrom(FROM_ADDRESS);
        message.setSubject("Photo Logger 비밀번호찾기 안내 메일입니다.");
        message.setText("비밀번호는 "+pwd+" 입니다.");
        mailSender.send(message);
    }

}
