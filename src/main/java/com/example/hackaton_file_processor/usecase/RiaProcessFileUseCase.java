package com.example.hackaton_file_processor.usecase;

import com.example.hackaton_file_processor.gateway.parser.FileParser;
import com.example.hackaton_file_processor.gateway.parser.FileParserFactory;
import com.example.hackaton_file_processor.gateway.s3.S3ReaderImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiaProcessFileUseCase {

    private final S3ReaderImpl s3Reader;
    private final FileParserFactory fileParserFactory;
    private final RiaProcessRecordUseCase riaProcessRecordUseCase;

    public void execute(String bucket, String key) {
        log.info("Iniciando processamento do arquivo: bucket={}, key={}", bucket, key);

        try (InputStream inputStream = s3Reader.readFile(bucket, key)) {

            FileParser parser = fileParserFactory.getParserForKey(key);

            parser.parse(inputStream, recordDTO -> {
                try {
                    riaProcessRecordUseCase.execute(recordDTO);
                } catch (Exception e) {
                    log.error("Erro ao processar linha do arquivo {}: {}", key, e.getMessage(), e);
                }
            });

            log.info("Processamento concluído para arquivo: {}", key);

        } catch (Exception e) {
            log.error("Erro ao processar arquivo {}: {}", key, e.getMessage(), e);
        }
    }
}