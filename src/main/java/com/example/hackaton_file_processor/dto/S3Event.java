package com.example.hackaton_file_processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class S3Event {
    @JsonProperty("Records")
    private List<Record> records;

    @Data
    public static class Record {
        private String eventSource;
        private String eventName;
        private S3 s3;
    }

    @Data
    public static class S3 {
        private Bucket bucket;
        private ObjectData object;
    }

    @Data
    public static class Bucket {
        private String name;
    }

    @Data
    public static class ObjectData {
        private String key;
    }
}