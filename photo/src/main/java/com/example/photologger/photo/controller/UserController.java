package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // 유저 조회
    @GetMapping("/{idx}")
    public String findUser(
        @PathVariable(value = "idx")
            int idx
    ) throws Exception {

        List<User> userList = userService.findOne(idx);
        for (User user : userList) {
            System.out.println(user);
        }
        return "ok";
    }

    //비밀번호 변경
    @PostMapping("/setpassword/{idx}")
    public String updatePassword(@PathVariable(value = "idx") Integer idx,
        @RequestBody User user) throws Exception {
        user.setIdx(idx);
        System.out.println(user.getIdx() + "님의 비밀번호가" + user.getPassword() + "변경되었습니다.");
        userService.UpdatePassword(user);
        return "ok";

    }
}