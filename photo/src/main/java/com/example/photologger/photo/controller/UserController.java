package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.EmailService;
import com.example.photologger.photo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/{idx}")
    @ResponseBody
    public User findUser(
        @PathVariable(value = "idx")
            Integer idx
    ) throws Exception {

        User user = userService.findOne(idx);
        return user;
    }

    // 아이디 찾기
    @GetMapping("/findId")
    public String findUserId(@RequestBody User user) {
        return "ok";
    }

    // 비밀번호 찾기
    @PostMapping("/findPwd")
    public String findPwd(@RequestBody User user)
        throws Exception {

        User user1 = null;
        user1 = userService.findpwd(user);
        log.info(user1.toString());

        if (user1 == null) { // 예외처리 확인
            return "false";
        } else {
            String email = user1.getEmail();
            log.info(user1.toString());
            emailService.sendMail(email,user1);
            return "true";
        }
    }


    // 비밀번호 변경
    @PostMapping("/setpassword/{idx}")
    public String updatePassword(@PathVariable(value = "idx") Integer idx,
        @RequestBody User user) throws Exception {
        user.setIdx(idx);
        log.info(user.getIdx() + "님의 비밀번호가" + user.getPassword() + "변경되었습니다.");
        userService.UpdatePassword(user);
        return "true";
    }
}