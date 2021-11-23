package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.SmtpConstant;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Random;
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

    public void SendJoinMail(String adress) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = getlink(adress);
        message.setTo(adress);
        message.setFrom(SmtpConstant.FROM_ADDRESS);
        message.setSubject(SmtpConstant.SMTP_JOIN_TITLE_MESSAGE);
        message.setText(SmtpConstant.SMTP_JOIN_MAIN_MESSAGE.replaceAll("\\$link", link));
        mailSender.send(message);
    }

    public String getlink(String email) {
        String str = "http://localhost:8080/api/accounts/emailcornfirm/";
        str += email;
        return str;
    }

    //랜덤 난수 만들기
    public String getTempPassword() {
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 20; i++) {
            if (rnd.nextBoolean()) {
                buf.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                buf.append((rnd.nextInt(10)));
            }
        }
        return buf.toString();
    }
}

