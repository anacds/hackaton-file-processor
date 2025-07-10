package com.example.hackaton_file_processor.usecase;

import com.example.hackaton_file_processor.dto.RecordDTO;
import com.example.hackaton_file_processor.gateway.fhir.FhirPayloadMapper;
import com.example.hackaton_file_processor.gateway.fhir.FhirPayloadMapperFactory;
import com.example.hackaton_file_processor.gateway.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiaProcessRecordUseCase {

    private final FhirPayloadMapperFactory mapperFactory;
    private final KafkaPublisher kafkaPublisher;

    public void execute(RecordDTO recordDTO) {
        log.info("Processando registro RIA: {}", recordDTO);

        try {
            var mapper = mapperFactory.getMapper();
            var payload = mapper.map(recordDTO);

            log.info("Payload: {}", payload);

            //kafkaPublisher.publish(payload);

            log.info("Registro RIA processado e publicado no Kafka com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao processar registro RIA: {}", e.getMessage(), e);
            throw e;
        }
    }
}