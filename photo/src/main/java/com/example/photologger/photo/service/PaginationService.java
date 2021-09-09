package com.example.photologger.photo.service;

import com.example.photologger.photo.constants.Constants;
import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.mapper.PaginationMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaginationService {

    @Autowired
    PaginationMapper paginationMapper;

    public List<Gallary> getGallery(Integer cursor) {

        return paginationMapper.cursorPagination(cursor, Constants.TAKE);
    }


}
