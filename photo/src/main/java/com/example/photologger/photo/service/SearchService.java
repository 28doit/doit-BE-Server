package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.Constants;
import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.User;
import com.example.photologger.photo.mapper.SearchMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final SearchMapper searchMapper;

    @Autowired
    public SearchService(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    public List<Gallery> titleSearch(Integer cursor, String word){
            return searchMapper.titleSearch(cursor, Constants.SEARCH_TAKE,word);
    }

    public List<User> sellerSearch(Integer cursor, String seller){
        return searchMapper.sellerSearch(cursor,Constants.SEARCH_TAKE, seller);
    }


}
