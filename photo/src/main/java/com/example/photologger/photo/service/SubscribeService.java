package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.Constants;
import com.example.photologger.photo.domain.Subscribe;
import com.example.photologger.photo.mapper.SubscribeMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;

    @Autowired
    public SubscribeService(SubscribeMapper subscribeMapper) {
        this.subscribeMapper = subscribeMapper;
    }

    /*사진 구독*/
    public void subscribeGallery(Subscribe subscribe) {
        subscribeMapper.subscirbeGallery(subscribe);
    }

    /*작가 구독*/
    public void subscribeUser(Subscribe subscribe) {
        subscribeMapper.subscirbeUser(subscribe);
    }

    /*구독한 사진 페이지 네이션*/
    public List<Subscribe> getSubscribeGallery(Integer cursor, Integer idx) {
        return subscribeMapper.subscirbeGalleryCursorPagination(cursor, idx, Constants.SUBSCRIVE_TAKE);
    }

    /*구독한 사진 페이지 네이션*/
    public List<Subscribe> getSubscribeUser(Integer cursor, Integer idx) {
        return subscribeMapper.subscirbeUserCursorPagination(cursor, idx, Constants.SUBSCRIVE_TAKE);
    }
}