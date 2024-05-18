package ru.ivanov.march.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanov.march.chat.application.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final Logger serverlogger = LogManager.getLogger(HttpServer.class.getName());

    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverlogger.trace("Сервер запущен на порту: {}", port);
            this.dispatcher = new Dispatcher();
            serverlogger.trace("Диспечер проинициализирован");
            Storage.init();
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    byte[] buffer = new byte[8192];
                    int n = socket.getInputStream().read(buffer);
                    if (n > 0) {
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);
                        dispatcher.execute(request, socket.getOutputStream());
                    }
                }
            }
        } catch (IOException e) {
            serverlogger.error("Что-то пошло не так {}", e.getMessage());
        }
    }
}
