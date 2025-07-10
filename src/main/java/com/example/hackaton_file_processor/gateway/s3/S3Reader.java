package com.example.hackaton_file_processor.gateway.s3;

import java.io.InputStream;

public interface S3Reader {
    InputStream readFile(String bucket, String key);
}
