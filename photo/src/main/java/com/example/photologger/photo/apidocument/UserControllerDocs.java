package com.example.photologger.photo.apidocument;

import com.example.photologger.photo.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= "User Controller")
public interface UserControllerDocs {


        // 유저 조회
        @ApiOperation(value = "유저 조회", notes = "유저 조회")
        public User findUser() throws Exception;

        // 아이디 찾기
        @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기")
        public String findUserId();

        // 비밀번호 찾기
        @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
        public String findPwd() throws Exception;

        // 비밀번호 변경
        @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경")
        public String updatePassword() throws Exception;



}
