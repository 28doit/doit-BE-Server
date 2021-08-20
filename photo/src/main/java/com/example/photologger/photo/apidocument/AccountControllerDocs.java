package com.example.photologger.photo.apidocument;

import com.example.photologger.photo.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

@Api(tags= "Accounts Controller")
public interface AccountControllerDocs {


    @ApiOperation(value = "회원가입 페이지", notes = "회원가입의 정보가 있는 폼으로 보내준다.")
    public ModelAndView join ();

    @ApiOperation(value = "회원가입", notes = "회원가입")
    public String Join(@ApiParam(value = "한명의 개인정보", required = true) User user)
        throws Exception;

    @ApiOperation(value = "로그인 페이지", notes = "로그인 정보가있는 폼으로 보내준다.")
    public String login();

    @ApiOperation(value = "로그인", notes = "로그인.")
    public String Login();

    @ApiOperation(value = "로그아웃", notes = "로그아웃.")
    public String logout(HttpServletRequest request, HttpServletResponse response);

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
    public void PassWord_Reset();

    @ApiOperation(value = "Id 찾기", notes = "id찾기")
    public String Id_Find();

    @ApiOperation(value = "회원 삭제", notes = "회원삭제는 ??일의 보류기간을 가집니다.")
    public String Delete_User();

}