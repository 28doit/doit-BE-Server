package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.SmtpConstant;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    UserMapper userMapper;
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //비밀 번호 찾기 안내 메일
    public Object sendFindPasswordMail(String adress, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        String expwd = getTempPassword();
        message.setTo(adress);
        message.setFrom(SmtpConstant.FROM_ADDRESS);
        message.setSubject(SmtpConstant.SMTP_PASSWORD_TITLE_MESSAGE); //utills 메서드 함수 참고
        message.setText(SmtpConstant.SMTP_PASSWORD_ALERT_MESSAGE.replaceAll("\\$expwd", expwd));
        user.setPassword(expwd);
        userMapper.updatePassword(user);
        mailSender.send(message);
        return null;
    }

    public void SendJoinMail(String adress){
        SimpleMailMessage message = new SimpleMailMessage();
        String link = getlink(adress);
        message.setTo(adress);
        message.setFrom(SmtpConstant.FROM_ADDRESS);
        message.setSubject(SmtpConstant.SMTP_JOIN_TITLE_MESSAGE);
        message.setText(SmtpConstant.SMTP_JOIN_MAIN_MESSAGE.replaceAll("\\$link", link));
        mailSender.send(message);
    }

    public String getlink(String email){
        String str = "http://localhost:8080/api/accounts/emailcornfirm/";
        str += email;
        return str;
    }

    //임시 비밀번호 생성 - UUID 자리수 제한 가능함으로써 변경
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
