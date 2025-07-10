package com.example.hackaton_file_processor.gateway.fhir;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FhirPayloadMapperFactory {

    private final RiaFhirPayloadMapper riaFhirPayloadMapper;

    public FhirPayloadMapper getMapper() {
        return riaFhirPayloadMapper;
    }
}