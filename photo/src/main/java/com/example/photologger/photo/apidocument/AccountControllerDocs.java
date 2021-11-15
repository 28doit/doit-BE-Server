package com.example.photologger.photo.apidocument;

import com.example.photologger.photo.domain.ReturnUser;
import com.example.photologger.photo.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags= "Accounts Controller")
public interface AccountControllerDocs {


    @ApiOperation(value = "회원가입", notes = "회원가입")
    public ResponseEntity JoIn(@ApiParam(value = "한명의 개인정보") User user);

    @ApiOperation(value = "로그인", notes = "로그인.")
    public ReturnUser login(@RequestBody @ApiParam(value = "이메일(ID) & password",required = true) Map<String,String> userIdPassword);

    @ApiOperation(value = "이메일 중복검사",notes = "회원가입중 이메일을 검증할 코드입니다.")
    public Object email_check(@RequestBody @ApiParam(value = "email") String email);

    @ApiOperation(value = "Token 검증",notes = "JWT검증하는 코드입니다.")
    public HashMap token_Expiration(@RequestParam(name = "token")@ApiParam(value = "token") String token, @RequestParam(name = "email") @ApiParam(value = "email") String email);

}
