package com.example.hackaton_file_processor.gateway.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileParserFactoryImpl implements FileParserFactory {

    private final Map<String, FileParser> parserMap;

    @Override
    public FileParser getParserForKey(String key) {
        String extension = getFileExtension(key).toLowerCase();
        FileParser parser = parserMap.get(extension);
        if (parser == null) {
            throw new IllegalArgumentException("Extensão não compatível: " + extension);
        }
        return parser;
    }

    private String getFileExtension(String key) {
        int lastDot = key.lastIndexOf('.');
        if (lastDot == -1 || lastDot == key.length() - 1) {
            throw new IllegalArgumentException("Arquivo sem extensão: " + key);
        }
        return key.substring(lastDot + 1);
    }
}