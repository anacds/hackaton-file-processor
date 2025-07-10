package com.example.hackaton_file_processor.gateway.fhir;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompositionResource {
    private String resourceType;
    private Meta meta;
    private String status;
    private Type type;
    private Subject subject;
    private String date;
    private List<Author> author;
    private String title;
    private List<Section> section;

    @Data @Builder
    public static class Meta {
        private List<String> profile;
    }

    @Data @Builder
    public static class Type {
        private List<Coding> coding;
    }

    @Data @Builder
    public static class Coding {
        private String system;
        private String code;
    }

    @Data @Builder
    public static class Subject {
        private Identifier identifier;
    }

    @Data @Builder
    public static class Author {
        private Identifier identifier;
    }

    @Data @Builder
    public static class Identifier {
        private String system;
        private String value;
    }

    @Data @Builder
    public static class Section {
        private List<EntryRef> entry;
    }

    @Data @Builder
    public static class EntryRef {
        private String reference;
    }
}