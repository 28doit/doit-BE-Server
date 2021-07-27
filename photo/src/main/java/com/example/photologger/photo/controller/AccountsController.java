package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import javax.servlet.http.*;

@Slf4j
@RequestMapping("/accounts")
@RestController
public class AccountsController {

    private AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService)
    {
        this.accountsService=accountsService;
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/new")
    public ModelAndView join () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accounts/createUserForm");
        return modelAndView;
    }
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String JoIn(@RequestBody User user)
    {
        log.info(user.toString());
        accountsService.join(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accounts/createUserForm");
        return user.toString();
    }
    @GetMapping("/login")
    public String login(){
        return "user/login/login";
    }

    @PostMapping("/login")
    public String Login()
    {

        return "redirect:/";
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
