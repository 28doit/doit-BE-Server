package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.UserService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public String findUser(@PathVariable(value = "idx") int idx)
            throws Exception {
        List<User> userList = userService.findOne(idx);

        for (User user : userList) {
            System.out.println(user);
        }
        return "ok";
    }
    //비밀번호 변경
    @GetMapping("update_password")
    public String updatePassword(@RequestParam(value = "updatepw",
            defaultValue = "", required = false) String email, User user){
            user.setEmail(email);
            log.info(user.toString());
            userService.UpdatePassword(user);
            return "ok";
    }
}