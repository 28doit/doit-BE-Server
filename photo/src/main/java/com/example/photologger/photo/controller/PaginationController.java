package com.example.photologger.photo.controller;


import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.service.PaginationService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/pagination")
@RestController
public class PaginationController {

    @Autowired
    PaginationService paginationService;


    @GetMapping("/cursor/{cursor}")
    public List<Gallery> cursorPagination(@PathVariable("cursor") Integer cursor){
        return paginationService.getGallery(cursor);
    }



}
