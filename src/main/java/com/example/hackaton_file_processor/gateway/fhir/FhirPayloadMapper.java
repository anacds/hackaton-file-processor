package com.example.hackaton_file_processor.gateway.fhir;

import com.example.hackaton_file_processor.dto.RecordDTO;

public interface FhirPayloadMapper {
    String map(RecordDTO recordDTO);
}