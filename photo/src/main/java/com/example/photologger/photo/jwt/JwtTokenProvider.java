package com.example.photologger.photo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // 토큰 생성, 검증

    private long tokenValidTime = 30*60*1000L;
    @Value("${jwt.secret}")
    private String secretKey;
    private final UserDetailsService  userDetailsService;

    public String createToken(String userPk){
        // Claims claims = Jwts.claims().setSubject(userPk);
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);//시크릿키 바이트화
        Key key = new SecretKeySpec(secretKeyBytes,SignatureAlgorithm.HS256.getJcaName());  //시크릿키 base64
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userPk) //아이디로만들경우 현재시점에 사용하지않음.
                .setHeaderParam("typ","jwt")
               // .claim("user",userPk)         //모든유저에 정보를 넘기는경우 현시점 사용하지않음.
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(key,SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {

        return Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

