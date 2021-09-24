package com.example.photologger.photo.controller;

import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.PaymentMapper;
import com.example.photologger.photo.service.AccountsService;
import com.example.photologger.photo.service.PaymentSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    PaymentSerivce paymentSerivce;
    @Autowired
    AccountsService accountsService;
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    AccountsMapper accountsMapper;

    @PostMapping(value = "/check",consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean check(@RequestBody Map<String,String> mid)
    {

        return paymentSerivce.check(mid);

    }
    @GetMapping("/history")
    public Object history(@RequestParam(name="token")String token,
                          @RequestParam(name = "start_history")String start,
                          @RequestParam(name = "end_history")String end)
    {
            return paymentSerivce.history(token,start, end);
    }
}
