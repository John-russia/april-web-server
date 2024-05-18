package ru.ivanov.march.chat.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.HttpRequest;
import ru.ivanov.march.chat.application.Item;
import ru.ivanov.march.chat.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductsProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(CreateNewProductsProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.safe(item);
        logger.trace("Удаленно создан товар {}, id: {}, цена: {}", item.getTitle(), item.getId(), item.getPrice());
        String jsonOutItem = gson.toJson(item);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: aplication/json\r\n\r\n" + jsonOutItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
