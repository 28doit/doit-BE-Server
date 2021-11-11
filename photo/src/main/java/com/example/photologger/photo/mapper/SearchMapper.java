package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {

    List<Gallary> titleSearch(
        @Param("cursor") Integer cursor,
        @Param("take") Integer take,
        @Param("word") String word
    );

    List<User> sellerSearch(
        @Param("cursor") Integer cursor,
        @Param("take") Integer take,
        @Param("seller") String seller
    );

}
