package ru.ivanov.march.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.application.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private static final Logger dispatcherlogger = LogManager.getLogger(Dispatcher.class.getName());
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownOperationRequestProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("GET /calc", new CalculatorRequestProcessor());
        this.router.put("GET /hello", new HelloWorldRequestProcessor());
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("POST /items", new CreateNewProductsProcessor());
        this.unknownOperationRequestProcessor = new UnknownOperationalRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        if (!router.containsKey(httpRequest.getRouteKey())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream);
            return;
        }
        dispatcherlogger.trace("Перенаправление на метод: {}", router.get(httpRequest.getRouteKey()).getClass());
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream);
    }
}
