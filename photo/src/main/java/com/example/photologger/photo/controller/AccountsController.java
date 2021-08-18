package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import com.example.photologger.photo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**/
@Slf4j
@RequestMapping("/accounts")
@RestController
public class AccountsController implements iAccountController{


    private final AccountsService accountsService;
    private final UserService userService;
    @Autowired
    public AccountsController(AccountsService accountsService,UserService userService)
    {
        this.accountsService=accountsService;
        this.userService=userService;

    }
    /**
     * 회원가입
     */
//    @GetMapping(value = "/new")
//    public ModelAndView JoIn() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/accounts/createUserForm");
//        return modelAndView;
//    }
    @Override
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity JoIn(@RequestBody User user) {
//        //비밀번호 암호화(미사용코드) 프론트쪽에서 암호화예정.
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        accountsService.join(user);
        log.info(user.getName()+"님의 회원가입이 정상적으로 완료되었습니다");
       return new ResponseEntity(HttpStatus.OK);
    }
    @Override
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ReturnUser login(@RequestBody Map<String, String> userIdPassword) {
       log.info(userIdPassword.toString());
        return accountsService.login(userIdPassword);
    }
    @Override
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
    @Override
    @PostMapping("/password-reset")
    public void PassWord_Reset()
    {

    }
    @Override
    @PostMapping("/id-find")
    public String Id_Find()
    {
        return "redirect:/";
    }
    @Override
    @PostMapping("/deleteUser")
    public String Delete_User()
    {
        return "redirect:/";
    }
    @Override
    @GetMapping("/new/email-check")
    public Object email_check(@RequestParam(name = "email") String email)
    {
        log.info(email);
        return accountsService.email_Check(email);
    }
    @Override
    @GetMapping("/token-check")
    @ResponseBody
    public HashMap token_Expiration(@RequestParam(name = "token")String token, @RequestParam(name = "email")String email)
    {
        log.info(token+email);
        return accountsService.token_Expiration(token,email);
    }
}
