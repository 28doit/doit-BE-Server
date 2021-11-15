package com.example.photologger.photo.controller;

import com.example.photologger.photo.service.PaymentSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("cash")
public class CashController {
    @Autowired
    PaymentSerivce paymentSerivce;

    @PostMapping(value = "/payment",consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean check(@RequestBody Map<String,String> mid)
    {
        return paymentSerivce.check(mid);
    }
    @GetMapping("/payment")
    public Object history(@RequestParam(name="token")String token,
                          @RequestParam(name = "start_history")String start,
                          @RequestParam(name = "end_history")String end)
    {
            return paymentSerivce.history(token,start, end);
    }

}
