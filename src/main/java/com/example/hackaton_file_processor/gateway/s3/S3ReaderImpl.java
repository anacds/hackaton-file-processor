package com.example.hackaton_file_processor.gateway.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.core.ResponseInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ReaderImpl implements S3Reader {

    private final S3Client s3Client;

    @Override
    public InputStream readFile(String bucket, String key) {
        log.info("Lendo arquivo do S3: bucket={}, key={}", bucket, key);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
        return s3Object;
    }
}