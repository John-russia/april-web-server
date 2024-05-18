package ru.ivanov.march.chat.application.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorRequestProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(CalculatorRequestProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        int a = Integer.parseInt(httpRequest.getParameter("a"));
        int b = Integer.parseInt(httpRequest.getParameter("b"));
        int result = a + b;
        logger.trace("От клиента получены значения a = {}, b = {}", a, b);
        String outMessage = a + " + " + b + " = " + result;
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>" + outMessage + "<h1><body><html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        logger.trace("Клиенту отправлен результат вычисления {}", result);
    }
}
