package com.example.hackaton_file_processor.gateway.fhir;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RiaFhirPayload {

    private String resourceType;
    private Meta meta;
    private Identifier identifier;
    private String type;
    private String timestamp;
    private List<Entry> entry;

    @Data
    @Builder
    public static class Meta {
        private String lastUpdated;
        private List<String> profile;
    }

    @Data
    @Builder
    public static class Identifier {
        private String system;
        private String value;
    }

    @Data
    @Builder
    public static class Entry {
        private String fullUrl;
        private Object resource;
    }

    @Data
    @Builder
    public static class Resource {
        private String resourceType;
        private Meta meta;
        private String status;

        private Type type;
        private Subject subject;
        private String date;
        private List<Author> author;
        private String title;
        private List<Section> section;

        private VaccineCode vaccineCode;
        private Patient patient;
        private String occurrenceDateTime;
        private Manufacturer manufacturer;
        private String lotNumber;
        private String expirationDate;
        private Site site;
        private Route route;
        private List<Performer> performer;
        private List<ProtocolApplied> protocolApplied;
    }

    @Data
    @Builder
    public static class Type {
        private List<Coding> coding;
    }

    @Data
    @Builder
    public static class Subject {
        private Identifier identifier;
    }

    @Data
    @Builder
    public static class Author {
        private Identifier identifier;
    }

    @Data
    @Builder
    public static class Section {
        private List<EntryRef> entry;
    }

    @Data
    @Builder
    public static class EntryRef {
        private String reference;
    }

    @Data
    @Builder
    public static class Coding {
        private String system;
        private String code;
    }

    @Data
    @Builder
    public static class VaccineCode {
        private List<Coding> coding;
    }

    @Data
    @Builder
    public static class Patient {
        private Identifier identifier;
    }

    @Data
    @Builder
    public static class Manufacturer {
        private Identifier identifier;
    }

    @Data
    @Builder
    public static class Site {
        private List<Coding> coding;
    }

    @Data
    @Builder
    public static class Route {
        private List<Coding> coding;
    }

    @Data
    @Builder
    public static class Performer {
        private Actor actor;
    }

    @Data
    @Builder
    public static class Actor {
        private String reference;
    }

    @Data
    @Builder
    public static class ProtocolApplied {
        private List<Extension> extension;
        private String doseNumberString;
    }

    @Data
    @Builder
    public static class Extension {
        private String url;
        private ValueCodeableConcept valueCodeableConcept;
        private List<SubExtension> extension;
    }

    @Data
    @Builder
    public static class ValueCodeableConcept {
        private List<Coding> coding;
    }

    @Data
    @Builder
    public static class SubExtension {
        private String url;
        private String valueString;
    }
}