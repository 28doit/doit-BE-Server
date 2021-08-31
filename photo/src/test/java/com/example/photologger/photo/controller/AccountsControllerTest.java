package com.example.photologger.photo.controller;

import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountsControllerTest {
    @Autowired
    AccountsMapper accountsMapper;
    @Test
    void joIn() {
    }

    @Test
    void login() {
       accountsMapper.findEmail("test@asd.com");
    }

    @Test
    void logout() {
    }

    @Test
    void passWord_Reset() {
    }

    @Test
    void id_Find() {
    }

    @Test
    void delete_User() {
    }
}