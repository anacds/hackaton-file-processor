package com.example.hackaton_file_processor.usecase;

import com.example.hackaton_file_processor.dto.RecordDTO;
import com.example.hackaton_file_processor.gateway.fhir.FhirPayloadMapperFactory;
import com.example.hackaton_file_processor.gateway.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiaProcessRecordUseCase {

    private final FhirPayloadMapperFactory mapperFactory;
    private final KafkaPublisher kafkaPublisher;
    private final Validator validator;

    public void execute(RecordDTO recordDTO) {
        log.info("Processando registro RIA: {}", recordDTO);

        try {

            validate(recordDTO);

            var mapper = mapperFactory.getMapper();
            var payload = mapper.map(recordDTO);

            log.info("Payload: {}", payload);

            kafkaPublisher.publishSuccess(payload);

            log.info("Registro RIA processado e publicado no Kafka com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao processar registro RIA: {}", e.getMessage(), e);
            kafkaPublisher.publishError(recordDTO, e.getMessage());
            throw e;
        }
    }

    private void validate(RecordDTO dto) {
        Set<ConstraintViolation<RecordDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String errorMsg = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validação falhou: " + errorMsg);
        }
    }
}