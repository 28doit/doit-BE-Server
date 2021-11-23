package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.ResponseDto;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.EmailService;
import com.example.photologger.photo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
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
    @GetMapping("/findid")
    public void findUserId(@RequestBody String name, String phoneNumber) {
        userService.findId(name, phoneNumber);

    }

    // 비밀번호 찾기
    @PostMapping("/findPwd")
    public ResponseEntity findPwd(@RequestBody User user) {
        User user1 = null;
        user1 = userService.findpwd(user);
        String email = user1.getEmail();
        return new ResponseEntity(
            ResponseDto
                .builder()
                .data(emailService.sendFindPasswordMail(email, user1))
                .build()
            , HttpStatus.OK);
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