
package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import com.example.photologger.photo.service.EmailService;
import com.example.photologger.photo.service.GalleryService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RequestMapping("/accounts")
@RestController
public class AccountsController {

    private final AccountsService accountsService;
    private final EmailService emailService;
    private final GalleryService galleryService;

    @Autowired
    public AccountsController(AccountsService accountsService,
        EmailService emailService,
        GalleryService galleryService) {
        this.accountsService = accountsService;
        this.emailService = emailService;
        this.galleryService = galleryService;
    }


    @PostMapping(value = "/new")
    public ResponseEntity JoIn(User user,
        @RequestParam(value = "images") MultipartFile multipartFile
    ) throws IOException {
//        //비밀번호 암호화(미사용코드) 프론트쪽에서 암호화예정.
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //DB User 데이터 저장

        // 이미지 경로
        String path = "profile";

        //회원가입
        accountsService.join(user);

        //이메일 인증
        emailService.SendJoinMail(user.getEmail());

        //이미지 전송
        user.setProfileImageLocation(galleryService.profileImagejoin(multipartFile, user));

        log.info(user.getName() + "님의 회원가입이 정상적으로 완료되었습니다");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/emailcornfirm/{email}")
    public String checkEmail(@PathVariable(value = "email") String email) {
        log.info(email);
        accountsService.emailCheck(email);
        log.info("아매알 안중이 완료 되었습니다.");
        return "true";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ReturnUser login(@RequestBody Map<String, String> userIdPassword) {
        log.info(userIdPassword.toString());
        return accountsService.login(userIdPassword);
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

    @GetMapping("/new/email-check")
    public Object email_check(@RequestParam(name = "email") String email) {
        log.info(email);
        return accountsService.email_Check(email);
    }

    @GetMapping("/token-check")
    @ResponseBody
    public HashMap token_Expiration(@RequestParam(name = "token") String token,
        @RequestParam(name = "email") String email) {
        log.info(token + email);
        return accountsService.token_Expiration(token, email);
    }
}

