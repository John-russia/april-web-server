package ru.ivanov.march.chat.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.HttpServer;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class Storage {

    private static final Logger storagelogger = LogManager.getLogger(Storage.class.getName());
    private static List<Item> items;

    public static void init() {
        storagelogger.trace("Хранилище проинициализировано");
        items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(new Item("item " + i, 100 + (int) (Math.random() * 1000)));
        }
    }


    public static List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static void safe(Item item) {
        item.setId(UUID.randomUUID());
        items.add(item);
    }


}
