package ru.ivanov.march.chat.processors;

import ru.ivanov.march.chat.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UnknownOperationalRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 404 NOT FOUND"; //\r\nContent-Type: text/html\r\n\r\n<html><body><h1>UNKNOWN OPERATIONAL REQUEST<h1><body><html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
