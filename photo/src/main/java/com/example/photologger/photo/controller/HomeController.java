package com.example.photologger.photo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(tags= "HomeController")
public class HomeController {
    @ApiOperation(value = "홈페이지", notes = "root page")
    @GetMapping("/")
    public String home()
    {
        return "redirect:/";
    }
}
