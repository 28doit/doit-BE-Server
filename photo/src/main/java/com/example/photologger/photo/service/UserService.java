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
    private static final String FROM_ADDRESS = "ukidd002@gmail.com";

    public UserService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    //맴버 전체 조회
    public List<User> findOne(Integer idx) {
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

    //이메일 전송
    public void sendMail(String pwd, String address) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setFrom(FROM_ADDRESS);
        message.setSubject("Photologger 비밀 번호 찾기 안내 메일");
        message.setText("비밀번호는 " + pwd + "입니다.");
        mailSender.send(message);

    }
}
