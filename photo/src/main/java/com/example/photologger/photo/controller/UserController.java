package com.example.photologger.photo.controller;

import com.example.photologger.photo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private UserService userService = new UserService();

}
