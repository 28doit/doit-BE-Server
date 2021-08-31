package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private AccountsService accountsService;
    @Test
    void login() {

        //given
        User user = new User();
        user.setEmail("hello@naver.com");
        user.setPassword("1234");
        //when
        Map<String, String> usertest1 = new HashMap<String, String>();
        usertest1.put(user.getEmail(), user.getPassword());
        System.out.println(usertest1);

//        accountsService.login(usertest1);
//        }catch (NullPointerException e)
//        {
//            System.out.println("testt");
//            e.printStackTrace();
//        }


    }
}