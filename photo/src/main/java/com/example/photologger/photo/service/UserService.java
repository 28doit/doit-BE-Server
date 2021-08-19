package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.SmtpConstant;
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

    public UserService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    //맴버 전체 조회
    public User findOne(Integer idx) {
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
    //sql 인젝션 알아보기
    public void sendMail(String address) {

        SimpleMailMessage message = new SimpleMailMessage();
        String expwd = getTempPassword();
        message.setTo(address);
        message.setFrom(SmtpConstant.FROM_ADDRESS);
        message.setSubject("Photologger 비밀 번호 찾기 안내 메일"); //utills 메서드 함수 참고
        message.setText(SmtpConstant.SMTP_PASSWORD_ALERT_MESSAGE.replaceAll("\\$expwd", expwd));
        mailSender.send(message);
    }

    //임시 비밀번호 생성
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
