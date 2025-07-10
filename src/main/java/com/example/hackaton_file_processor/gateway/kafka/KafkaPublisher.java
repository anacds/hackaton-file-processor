package com.example.hackaton_file_processor.gateway.kafka;

import com.example.hackaton_file_processor.dto.RecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String successTopic = "${kafka-success-topic}";
    private final String errorTopic = "${kafka-error-topic}";

    public void publishSuccess(String fhirPayload) {
        log.info("Publicando payload de sucesso no Kafka");

        kafkaTemplate.send(successTopic, fhirPayload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Erro ao publicar payload no tópico {}: {}", successTopic, ex.getMessage(), ex);
                    } else {
                        log.info("Payload publicado com sucesso no tópico {}", successTopic);
                    }
                });
    }

    public void publishError(RecordDTO dto, String reason) {
        log.warn("Publicando payload de erro no Kafka");

        String errorPayload = String.format("""
                {
                    "record": "%s",
                    "error": "%s"
                }
                """, dto.toString(), reason);

        kafkaTemplate.send(errorTopic, errorPayload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Erro ao publicar payload de erro no tópico {}: {}", errorTopic, ex.getMessage(), ex);
                    } else {
                        log.info("Payload de erro publicado no tópico {}", errorTopic);
                    }
                });
    }
}