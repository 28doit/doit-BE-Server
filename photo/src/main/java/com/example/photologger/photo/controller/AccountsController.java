package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import javax.servlet.http.*;
/**/
@Slf4j
@RequestMapping("/accounts")
@RestController
@Api(tags= "Accounts Controller")
public class AccountsController {

    private AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService)
    {
        this.accountsService=accountsService;
    }
    /**/
    /**
     * 회원가입 페이지
     */
    @ApiOperation(value = "회원가입 페이지", notes = "회원가입의 정보가 있는 폼으로 보내준다.")
    @GetMapping("/new")
    public ModelAndView join () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accounts/createUserForm");
        return modelAndView;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Join(@RequestBody @ApiParam(value = "한명의 개인정보", required = true) User user)
    {
        log.info(user.toString());
        accountsService.join(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("accounts/createUserForm");
        return user.toString();
    }
    @ApiOperation(value = "로그인 페이지", notes = "로그인 정보가있는 폼으로 보내준다.")
    @GetMapping("/login")
    public String login(){
        return "user/login/login";
    }
    @ApiOperation(value = "로그인", notes = "로그인.")
    @PostMapping("/login")
    public String Login()
    {

        return "redirect:/";
    }
    @ApiOperation(value = "로그아웃", notes = "로그아웃.")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
    @PostMapping("/password-reset")
    public void PassWord_Reset()
    {

    }
    @ApiOperation(value = "Id 찾기", notes = "id찾기")
    @PostMapping("/id-find")
    public String Id_Find()
    {
        return "redirect:/";
    }
    @ApiOperation(value = "회원 삭제", notes = "회원삭제는 ??일의 보류기간을 가집니다.")
    @PostMapping("/deleteUser")
    public String Delete_User()
    {
        return "redirect:/";
    }

}