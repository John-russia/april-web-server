package ru.ivanov.march.chat.application.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HelloWorldRequestProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(HelloWorldRequestProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>Hello World<h1><body><html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        logger.trace("Отправили клиенту Hello World");
    }
}
