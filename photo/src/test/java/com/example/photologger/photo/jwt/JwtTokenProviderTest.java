package com.example.photologger.photo.jwt;

import com.example.photologger.photo.domain.ReturnUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Test
    void createToken() {
        /*
            모든정보를 넣어본경우 현시점 사용하지않음.
         */
//        User user = User.builder()
//                    .idx(1)
//                    .email("test@naver.com")
//                    .password("1234")
//                    .name("Test")
//                    .nickName("test123")
//                    .phoneNumber("12312341234")
//                    .sex(1)
//                    .profileImageLocation("/")
//                    .userYear("1234")
//                    .userMonth("12")
//                    .userDay("12")
//                    .type(1)
//                    .gallaryCount(1)
//                    .userSubscribeCount(1)
//                    .build();
        String user = "test@naver.com";
        ReturnUser user1 = new ReturnUser();
        user1.setToken(jwtTokenProvider.createToken(user));
        System.out.println(user1.getToken());;
        System.out.println(jwtTokenProvider.getUserPk(user1.getToken()));

    }

}