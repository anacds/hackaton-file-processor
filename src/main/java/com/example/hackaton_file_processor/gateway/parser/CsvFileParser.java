package com.example.hackaton_file_processor.gateway.parser;

import com.example.hackaton_file_processor.dto.RecordDTO;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@Component("csv")
@Slf4j
public class CsvFileParser implements FileParser {

    private final CsvMapper csvMapper;

    public CsvFileParser() {
        this.csvMapper = new CsvMapper();
    }


    @Override
    public void parse(InputStream inputStream, Consumer<RecordDTO> recordConsumer) {
        try {
            CsvSchema schema = csvMapper
                    .schemaFor(RecordDTO.class)
                    .withHeader()
                    .withColumnReordering(true);

            MappingIterator<RecordDTO> iterator =
                    csvMapper.readerFor(RecordDTO.class)
                            .with(schema)
                            .readValues(inputStream);

            while (iterator.hasNext()) {
                RecordDTO dto = iterator.next();
                recordConsumer.accept(dto);
            }

        } catch (IOException e) {
            log.error("Erro ao processar arquivo CSV", e);
            throw new RuntimeException("Erro ao ler arquivo CSV", e);
        }
    }
}