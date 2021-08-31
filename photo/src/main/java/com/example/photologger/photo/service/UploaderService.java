
package com.example.photologger.photo.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.photologger.photo.mapper.GallaryMapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UploaderService {

    @Autowired
    GallaryMapper gallaryMapper;

    @Autowired
    private final AmazonS3Client amazonS3Client;

    public UploaderService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    // 날짜
    Date date_now = new Date(System.currentTimeMillis());
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl; //버킷 주소

    public String upload(MultipartFile multipartFile) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String url;

        try {

            //파일 명 변경
            String saveFileName =
                "O" + fourteen_format.format(date_now) +multipartFile.getOriginalFilename();
            File file = new File( System.getProperty("user.dir") + saveFileName);
            multipartFile.transferTo(file);
            //원본 이미지 업로드
            uploadOnS3(saveFileName, file);

            BufferedImage transImage = ImageIO.read(file);
            BufferedImage thumbnailImage = Thumbnails.of(transImage).size(500,333).asBufferedImage();
            String saveFileName2 = "T" + fourteen_format.format(date_now) + multipartFile.getOriginalFilename(); //사간 이름 카테고리
            thumbUploadOns3(saveFileName2,thumbnailImage);

            url = defaultUrl + saveFileName;

        } catch (StringIndexOutOfBoundsException e) {
            url = null;
        }
        return url;
    }

    private void uploadOnS3(String findName,File file) {
        TransferManager transferManager = new TransferManager(this.amazonS3Client);
        PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        Upload upload = transferManager.upload(request);
        log.info("원본 이미지 저장 완료");
        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }


    private void thumbUploadOns3(String findName, BufferedImage file) throws IOException {

        // outputstream에 image객체를 저장
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(file, "png", os);

        //byte[]로 변환
        byte[] bytes = os.toByteArray();

        //metadata 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        objectMetadata.setContentType("image/png");

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        TransferManager transferManager = new TransferManager(this.amazonS3Client);
        PutObjectRequest request = new PutObjectRequest(bucket,findName ,byteArrayInputStream, objectMetadata);
        Upload upload = transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
