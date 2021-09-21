package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallary;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaginationMapper {

    List<Gallary> cursorPagination(
        @Param("cursor") Integer cursor
        ,@Param("take") Integer take
    );

    Integer gallerycount();

}
