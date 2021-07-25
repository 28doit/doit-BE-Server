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
        log.info(user.toString());
        accountsService.join(user);
        return "ok";
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
