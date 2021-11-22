package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.*;
import com.example.photologger.photo.jwt.JwtTokenProvider;
import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.PaymentMapper;
import com.example.photologger.photo.mapper.UserMapper;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountsService {
    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PaymentMapper paymentMapper;
    @Transactional
    public int join(User user) {
        //validateDuplicateuser(user); //중복 회원 검증

        //비밀번호 암호화
        accountsMapper.join(user);

        return user.getIdx();
    }

    public void emailCheck(String email){
        accountsMapper.emailCheck(email);
    }

    public ReturnUser login(Map<String,String> userIdPassword) {
        ReturnUser returnUser = new ReturnUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        log.info(userIdPassword.get("email"));
        try {
            User user = accountsMapper.findEmail(userIdPassword.get("email"))
                    //            User user= userRepository.findbyUsername(logInUser.get("username"))
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));
            if (!bCryptPasswordEncoder.matches(userIdPassword.get("password"), user.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            if(user.getAuthKey()==0) { //1로바꾸셈
                returnUser.setToken(jwtTokenProvider.createToken(user.getEmail()));
                returnUser.setIsValue(1);
                log.info(returnUser.getToken());
                returnUser.setEmail(user.getEmail());
                returnUser.setIdx(user.getIdx());
                return returnUser;
            }
            else
            {
                returnUser.setIsValue(2);
                log.info("현재 아이디가 가입처리 되지 않았습니다. E-mail을 확인해주세요.");
                return returnUser;
            }
        } catch (IllegalArgumentException e) {
            returnUser.setIsValue(0);
            log.info(e.getMessage());
            return returnUser;
        }
    }

    public boolean login_check(String token)
    {
        return jwtTokenProvider.validateToken(token);
    }

    public Object email_Check(String email)
    {
        ReturnCheck returncheck = new ReturnCheck();
        if(email.equals(""))
        {
            returncheck.setMsg("아무것도 입력하지 않으셨습니다.");
            returncheck.setValue(false);
            log.info(returncheck.toString());
            return returncheck;
        }
        try {
                accountsMapper.findEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("가입가능한 email 입니다."));
                 returncheck.setMsg("이미 회원가입된 email 입니다.");
                 returncheck.setValue(true);
                 log.info(returncheck.toString());
        }catch (IllegalArgumentException e) {
            returncheck.setMsg(e.getMessage());
            returncheck.setValue(false);
            log.info(returncheck.toString());
            return returncheck;
        }
        return returncheck;
    }
    public HashMap token_Expiration(String token,String email)
    {
        HashMap<String,Boolean> TrueAndFlase = new HashMap();
        List<Withdrawal> withdrawal = new ArrayList<>();
        int size=0;
        List<ReturnPhotosForSale> returnPhotosForSales = new ArrayList<>();
        int cumulativeSales=0;
        try {

            if (jwtTokenProvider.getUserPk(token).equals(email))
            {
                User user = accountsMapper.findEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("토큰에 정보와 일치하지 않습니다."));

                if (jwtTokenProvider.validateToken(token))
                {
                    log.info(user.toString());
                    HashMap<String,Object> tmp = new HashMap<>();
                    tmp.put("Token",jwtTokenProvider.validateToken(token));
                    tmp.put("Name",user.getName());
                    tmp.put("Email",user.getEmail());
                    tmp.put("Year",user.getUserYear());
                    tmp.put("Month",user.getUserMonth());
                    tmp.put("Day",user.getUserDay());
                    tmp.put("Sex",user.getSex());
                    tmp.put("PhoneNumber",user.getPhoneNumber());
                    tmp.put("Point",paymentMapper.userPoint(user.getIdx()));
                    tmp.put("NickName",user.getNickName());
                    tmp.put("SubScribeUser",userMapper.subScribe(user.getIdx()));
                    tmp.put("ProfileImagelocation",user.getProfileImageLocation());
                    returnPhotosForSales = userMapper.photosForSale(user.getIdx());
                    for(int i =0; i< returnPhotosForSales.size();i++)
                    {
                        size= size+paymentMapper.totalSales(returnPhotosForSales.get(i).getGalleryId()).size();
                    }
                    tmp.put("totalSales",size);
                    tmp.put("PhotosForSale",returnPhotosForSales);
                    withdrawal = paymentMapper.moneyWithdrawn(user.getIdx());
                    for(int i=0;i<withdrawal.size();i++)
                    {
                        cumulativeSales = cumulativeSales + withdrawal.get(i).getPoint();
                    }
                    cumulativeSales = cumulativeSales + paymentMapper.userPoint(user.getIdx()).getProfitPoint();
                    tmp.put("CumulativeSales",cumulativeSales);
                   // 판매중콘텐츠는 : 사진만
                  //  누적판매수 : 사진전부더해서
                  //      나를구독하고있는사람 반틈완료.
                    log.info("현재 사용가능한 토큰입니다. "+ "토큰 만료시간 : {} ");//수정하세요
                    return tmp;

                }
                //사실상 동작안하는부분(0.0몇초차이로 할수도)
                TrueAndFlase.put("Token", jwtTokenProvider.validateToken(token));
                log.info("test1");
                return TrueAndFlase;
            }
        }catch (ExpiredJwtException e) {
            TrueAndFlase.put("Token", jwtTokenProvider.validateToken(token));
            log.info("test2");
            log.info("해당 토큰은 이미 만료된 토큰입니다.   " + "토큰 만료시간 : {} " + e.getClaims().getExpiration());   //현재 디버그 동작안함 수정 필요
            return TrueAndFlase;
        }
        //사실상 동작안하는부분
        TrueAndFlase.put("Token",false);
        log.info("test3");
        return TrueAndFlase;
    }

    public String getUserPk(String token)
    {
        return jwtTokenProvider.getUserPk(token);
    }
}
