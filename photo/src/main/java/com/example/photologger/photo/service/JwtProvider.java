package com.example.photologger.photo.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}") //application.properties에 설정
    private String secretKey;  //시크릿키
    private final long ACCESS_TOKEN_VALID_TIME = 120*60*1000L; //분 * 초 * 1000L = 2시간

    private AccountsService accountsService;

    /**
     *  객체 초기화, secretKey를 base64로 인코딩한다.
     **/
    @PostConstruct
    protected void init()
    {
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
        log.info(secretKey.toString());
    }

    /**
     * 토큰생성
     */
   public String createToken(String userIDX, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userIDX);
        claims.put("roles",roles); //정보는 key / value쌍으로 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)  //정보저장
                .setIssuedAt(now)//토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime()+ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256,secretKey)

                .compact();
    }
}