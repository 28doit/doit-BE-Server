package com.example.photologger.photo.service;

import com.example.photologger.photo.domain.Gallary;
import com.example.photologger.photo.mapper.GallaryMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class GallaryService {

    @Autowired
    UploaderService uploaderService;

    @Autowired
    GallaryMapper gallaryMapper;

    public void gallaryInfoeSave(Gallary gallary, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {

            gallary.setGallaryImageLocation(uploaderService.upload(multipartFile));
            gallary.setGallaryName(multipartFile.getOriginalFilename());
            gallaryMapper.gallaryinfosave(gallary);
            log.info(gallary.toString());
        }
    }
}
