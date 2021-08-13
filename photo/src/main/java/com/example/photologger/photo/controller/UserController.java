package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


  /*// 유저 조회
    @GetMapping("/{idx}")
    @ResponseBody
    public String findUser(
        @PathVariable(value = "idx")
            Integer idx
    ) throws Exception {

        List<User> userList = userService.findOne(idx);
        for (User user : userList) {
            log.info(user.toString());
        }
        return userList;
    }*/

    // 아이디 찾기
    @GetMapping("/findId")
    public String findUserId(@RequestBody User user) {
        return "ok";
    }

    // 비밀번호 찾기
    @PostMapping("/findPwd")
    public String findPwd(@RequestBody User user)
        throws Exception {

        User user1 = userService.findpwd(user);
        log.info(user1.toString());

        if (user1 == null) {
            return "이메일에 정보가 없습니다.";
        } else {
            String pwd = user1.getPassword();
            String email = user1.getEmail();
            userService.sendMail(pwd, email);
            log.info(user1.toString());
            return "success";
        }
    }


    // 비밀번호 변경
    @PostMapping("/setpassword/{idx}")
    public String updatePassword(@PathVariable(value = "idx") Integer idx,
        @RequestBody User user) throws Exception {
        user.setIdx(idx);
        System.out.println(user.getIdx() + "님의 비밀번호가" + user.getPassword() + "변경되었습니다.");
        userService.UpdatePassword(user);
        return "ok";
    }

}