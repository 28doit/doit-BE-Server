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

@Controller
@RequestMapping("/accounts")
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
    public String JoIn(User form) throws Exception {

        accountsService.join(form);

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
