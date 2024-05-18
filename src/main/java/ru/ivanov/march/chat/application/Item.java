package ru.ivanov.march.chat.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.UUID;

public class Item {

    private static final Logger itemlogger = LogManager.getLogger(Item.class.getName());

    private UUID id;
    private String title;
    private int price;

    public Item() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item(String title, int price) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.price = price;
        itemlogger.trace("Создан товар {}, id: {}, цена: {}", title, id, price);

    }
}
