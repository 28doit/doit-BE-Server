package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.*;


@RequestMapping("/accounts")
@Controller
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
    public String JoIn(User form)
    {
        User user = new User();


        user.setEmail(form.getEmail());   //email
        user.setPassword(form.getPassword());
        user.setName(form.getName());    //이름
        user.setIdx(form.getIdx());        //idx
        user.setNicName(form.getNicName()); //닉네임
        user.setPhonenumber(form.getPhonenumber()); //휴대폰
        user.setSex(form.getSex());    //성별
        user.setProfile_img_loc(form.getProfile_img_loc());
        user.setYear(form.getYear());
        user.setMonth(form.getMonth());
        user.setDay(form.getDay());

        user.setCornfirm(form.getCornfirm());
        user.setType(form.getType());
        user.setGall_count(form.getGall_count());
        user.setUser_subscribe_count(form.getUser_subscribe_count());


        System.out.println(user.getEmail());
        System.out.println(user.getIdx());
        System.out.println(user.getPassword());
        System.out.println(user.getName());
        accountsService.join(user);

        return "redirect:/";
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
