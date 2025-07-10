package com.example.hackaton_file_processor.gateway.parser;

import com.example.hackaton_file_processor.dto.RecordDTO;

import java.io.InputStream;
import java.util.function.Consumer;

public interface FileParser {
    void parse(InputStream inputStream, Consumer<RecordDTO> consumer);
}