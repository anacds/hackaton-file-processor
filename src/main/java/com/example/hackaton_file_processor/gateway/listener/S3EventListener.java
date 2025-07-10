package com.example.hackaton_file_processor.gateway.listener;

import com.example.hackaton_file_processor.dto.S3Event;
import com.example.hackaton_file_processor.usecase.RiaProcessFileUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3EventListener {

    private final RiaProcessFileUseCase riaProcessFileUseCase;
    private final ObjectMapper objectMapper;

    @SqsListener("${file-events-queue}")
    public void listen(@Payload String messageJson) {
        log.info("Mensagem recebida do SQS: {}", messageJson);

        S3Event s3Event;

        try {
            s3Event = objectMapper.readValue(messageJson, S3Event.class);
        } catch (JsonProcessingException e) {
            log.error("Falha ao desserializar mensagem SQS. Ignorando...", e);
            return;
        }

        if (s3Event.getRecords() == null || s3Event.getRecords().isEmpty()) {
            log.warn("Mensagem ignorada: não contém Records válidos.");
            return;
        }

        s3Event.getRecords().forEach(record -> {
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();
            String eventSource = record.getEventSource();
            String eventName = record.getEventName();

            log.info("Analisando record: bucket={}, key={}, eventSource={}, eventName={}",
                    bucket, key, eventSource, eventName);

            if (!"aws:s3".equals(eventSource)
                    || !"ObjectCreated:Put".equals(eventName)
                    || !"arquivos-ria".equals(bucket)) {
                log.warn("Record ignorado: não atende aos critérios.");
                return;
            }

            try {
                riaProcessFileUseCase.execute(bucket, key);
                log.info("Arquivo processado com sucesso: bucket={}, key={}", bucket, key);
            } catch (Exception e) {
                log.error("Erro ao processar arquivo: bucket={}, key={}", bucket, key, e);
            }
        });
    }
}