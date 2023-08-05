package com.shallwe.domain.file.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;


    public String upload(MultipartFile multipartFile) throws IOException{
        String s3FileName = UUID.randomUUID() + "-" +
                multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getSize());
        amazonS3Client.putObject(bucket, s3FileName,
                multipartFile.getInputStream(),objMeta);
        return amazonS3Client.getUrl(bucket,s3FileName).toString();
    }
}
