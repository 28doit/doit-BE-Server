package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Gallery;
import com.example.photologger.photo.domain.JoinGalleryUserLU;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GalleryMapper {

    /* 갤러리 정보 저장 */
    void galleryinfosave(Gallery gallery);

    /* 프로필 이미지 업로드 */
    void updateProfileLocation(Integer idx, String profileImageLocation, String nickName, String password);

    /* 갤러리 조회 (Only gallery Info) */
    Gallery galleryLookUp(@Param("galleryId")Integer galleryId);

    /* 갤러리, 유저 조회 (Gallery And User Info) */
    JoinGalleryUserLU galleryAndUserLU(Integer galleryId, Integer idx);

    /* 갤러리 조회수 올리기 */
    void galleryViewCount(Integer galleryId);

    /* 갤러리 구매 횟수 올리기 */
    void galleryBuyCount(int galleryId);

}
