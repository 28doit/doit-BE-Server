package com.example.photologger.photo.controller;

import com.example.photologger.photo.apidocument.AccountControllerDocs;
import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.AccountsService;
import com.example.photologger.photo.service.EmailService;
import com.example.photologger.photo.service.GalleryService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private final GalleryService galleryService;
    private final AccountsService accountsService;
    private final EmailService emailService;

    @Autowired
    public AccountsController(AccountsService accountsService,
                              EmailService emailService,
        GalleryService galleryService) {
        this.accountsService = accountsService;
        this.emailService = emailService;
        this.galleryService = galleryService;
    }

    @PostMapping(value = "")
    public ResponseEntity JoIn(
        @RequestBody User user,
        @RequestParam(value = "images") MultipartFile multipartFile) throws IOException {

        accountsService.join(user);
        user.setProfileImageLocation(galleryService.profileImagejoin(multipartFile, user));
        emailService.SendJoinMail(user.getEmail());
        log.info(user.getName() + "님의 회원가입이 정상적으로 완료되었습니다");
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/emailcornfirm/{email}")
    public String checkEmail(@PathVariable(value = "email") String email) {
        log.info(email);
        accountsService.emailCheck(email);
        log.info("아매알 인중이 완료 되었습니다.");
        return "true";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ReturnUser login(@RequestBody Map<String, String> userIdPassword) {
        log.info(userIdPassword.toString());
        return accountsService.login(userIdPassword);
    }

    @GetMapping("/email")
    public Object email_check(@RequestParam(name = "email") String email) {
        log.info(email);
        return accountsService.email_Check(email);
    }

    @GetMapping("/token")
    @ResponseBody
    public HashMap token_Expiration(@RequestParam(name = "token") String token,
        @RequestParam(name = "email") String email) {
        log.info(token + email);
        return accountsService.token_Expiration(token, email);
    }
}
