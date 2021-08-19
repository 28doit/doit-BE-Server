package com.example.photologger.photo.controller;

import com.example.photologger.photo.apidocument.AccountControllerDocs;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**/
@Slf4j
@RequestMapping("/accounts")
@RestController

public class AccountsController implements AccountControllerDocs {

    private AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/new")
    public ModelAndView join() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accounts/createUserForm");
        return modelAndView;
    }

    @PostMapping(value = "/new")
    public String Join(@RequestBody User user)
        throws Exception {
        log.info(user.toString());
        accountsService.join(user);
        return "ok";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login/login";
    }

    @PostMapping("/login")
    public String Login() {

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
    public void PassWord_Reset() {

    }

    @PostMapping("/id-find")
    public String Id_Find() {
        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String Delete_User() {
        return "redirect:/";
    }

}