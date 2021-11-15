package com.example.photologger.photo.controller;

import com.example.photologger.photo.service.PaymentSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("item")
public class ItemController {
    @Autowired
    PaymentSerivce paymentSerivce;
    @PostMapping("/buy")
    public Object itemBuy(@RequestBody Map<String,Object> tmp)
    {
        int galleryid=Integer.parseInt(tmp.get("gallery_id").toString());
        String token = tmp.get("token").toString();
        log.info(galleryid+" "+token);
        return paymentSerivce.itemBuy(galleryid,token);
    }
    @GetMapping("/buy")
    public Object itemHistory(@RequestParam(name="token")String token,
                              @RequestParam(name = "start_history")String start,
                              @RequestParam(name = "end_history")String end)
    {
        return paymentSerivce.itemHistoryCheck(token,start,end);
    }
    @GetMapping("/cart")
    public Object cartCheck(@RequestParam(name="token") String token,
                            @RequestParam(name="email") String email)
    {
        return paymentSerivce.cartCheck(token,email);
    }
    @PostMapping("/cart")
    public Object cartInsert(@RequestBody Map<String,Object> tmp)
    {
        return paymentSerivce.cartInsert(tmp);
    }

    @PostMapping(value="/cart/buy",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object cartBuy(@RequestBody String json)
    {
        return paymentSerivce.cartBuy(json);
    }
    @DeleteMapping("/cart")
    public Object cartDelete(@RequestParam(name="token") String token,
                             @RequestParam(name="email") String email,
                             @RequestParam(name="gallery_id")String gallery_id)//여러개 받을거라 1,2,3으로 받고 자르는게 편할듯
    {
        return paymentSerivce.cartDelete(token,email,gallery_id);
    }
}
