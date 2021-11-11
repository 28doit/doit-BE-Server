package com.example.photologger.photo.controller;

import com.example.photologger.photo.domain.Subscribe;
import com.example.photologger.photo.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    public SubscribeController(
        SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    /*사진 구독*/
    @PostMapping("/gallery")
    public void subscribeGallery(
        @RequestBody Subscribe subscribe
    ) {
        subscribeService.subscribeGallery(subscribe);
    }

    /*작가 구독*/
    @PostMapping("/user")
    public void subscribeUser(
        @RequestBody Subscribe subscribe
    ) {
        subscribeService.subscribeUser(subscribe);
    }

//    /*구독한 사진 불러오기*/
//    @GetMapping("/cursor/{cursor}/{idx}")
//    public List subGalleryPagination(@PathVariable("cursor") Integer cursor)
//    {
//        return subscribeService.getSubscribeGallery(cursor);
//    }

}
