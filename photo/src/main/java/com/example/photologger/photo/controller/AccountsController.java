package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Slf4j
@RequestMapping("/accounts")
@RestController
public class AccountsController {
    /**
     * 회원가입
     */
    private AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService)
    {
        this.accountsService=accountsService;
    }

    @GetMapping("/new")
    public String JoInForm()
    {
        return "accounts/createUserForm";
    }

    @PostMapping("/new")
    public String JoIn(@RequestBody User user)
    {
//        //User user = new User();
//        user.setEmail(form.getEmail());   //email
//        user.setPassword(form.getPassword());
//        user.setU_name(form.getU_name());    //이름
//        user.setIdx(form.getIdx());        //idx
//        user.setNicName(form.getNicName()); //닉네임
//        user.setphoneNumber(form.getphoneNumber()); //휴대폰
//        user.setSex(form.getSex());    //성별
//        user.setProfile_img_loc(form.getProfile_img_loc());
//        user.setU_year(form.getU_year());
//        user.setU_month(form.getU_month());
//        user.setU_day(form.getU_day());
//
//        user.setCornfirm(form.getCornfirm());
//        user.setType(form.getType());
//        user.setGall_count(form.getGall_count());
//        user.setUser_subscribe_count(form.getUser_subscribe_count());


//        System.out.println("이메일 : " + user.getEmail());
//        System.out.println("idx : " + user.getIdx());
//        System.out.println("password : " +user.getPassword());
//        System.out.println("U_name" + user.getU_name());
        log.info(user.toString());
        accountsService.join(user);

        return "";
    }
    @GetMapping("/login")
    public String login(){
        return "user/login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
    @PostMapping("/password-reset")
    public void PassWord_Reset()
    {

    }
    @PostMapping("/id-find")
    public String Id_Find()
    {
        return "";
    }

}
