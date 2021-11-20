package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Subscribe;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscribeMapper {

    void subscirbeGallery(Subscribe subscribe);

    void subscirbeUser(Subscribe subscribe);

    List<Subscribe> subscirbeGalleryCursorPagination(
        @Param("cursor") Integer cursor,
        @Param("idx") Integer idx,
        @Param("take") Integer take
    );

    List<Subscribe> subscirbeUserCursorPagination(
        @Param("cursor") Integer cursor,
        @Param("idx") Integer idx,
        @Param("take") Integer take
    );


}
