package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallery;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaginationMapper {

    /*Cursor Pagination*/
    List<Gallery> cursorPagination(
        @Param("cursor") Integer cursor
        , @Param("take") Integer take
    );

}
