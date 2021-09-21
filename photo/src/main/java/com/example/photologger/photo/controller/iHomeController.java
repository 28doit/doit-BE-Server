package com.example.photologger.photo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= "HomeController")
public interface iHomeController {
        @ApiOperation(value = "홈페이지", notes = "root page")
        public String home();
}
