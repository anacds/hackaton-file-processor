package com.example.hackaton_file_processor.gateway.parser;

public interface FileParserFactory {
    FileParser getParserForKey(String key);
}