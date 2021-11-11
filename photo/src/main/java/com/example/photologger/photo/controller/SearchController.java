package com.example.photologger.photo.controller;


import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.service.SearchService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/title/{cursor}/{word}")
    public List<Gallary> titleSearch(
        @PathVariable("cursor") Integer cursor,
        @PathVariable("word") String word)
    {
        return searchService.titleSearch(cursor,word);
    }

    @GetMapping(value = "/seller/{cursor}/{seller}")
    public List<User> sellerSearch(
        @PathVariable("cursor") Integer cursor,
        @PathVariable("word") String seller
    ){
        return searchService.sellerSearch(cursor,seller);
    }

}
