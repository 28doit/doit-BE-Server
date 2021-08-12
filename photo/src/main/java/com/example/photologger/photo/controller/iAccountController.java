package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Api(tags= "Accounts Controller")
public interface iAccountController
{
    /**
     * 회원가입 페이지
     */
    @ApiOperation(value = "회원가입", notes = "회원가입")
    public ResponseEntity JoIn(@RequestBody @ApiParam(value = "한명의 개인정보",required = true) User user);

    @ApiOperation(value = "로그인", notes = "로그인.")
    public ReturnUser login(@RequestBody @ApiParam(value = "이메일(ID) & password",required = true)Map <String,String> userIdPassword);

    @ApiOperation(value = "로그아웃", notes = "로그아웃.")
    public String logout(HttpServletRequest request, HttpServletResponse response);

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
    public void PassWord_Reset();

    @ApiOperation(value = "Id 찾기", notes = "id찾기")
    public String Id_Find();

    @ApiOperation(value = "회원 삭제", notes = "회원삭제는 ??일의 보류기간을 가집니다.")
    public String Delete_User();




}
