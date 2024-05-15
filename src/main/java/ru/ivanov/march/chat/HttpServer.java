package ru.ivanov.march.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private ServerSocket serverSocket = null;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен, сокет сервера инициализирован.");
        } catch (IOException e) {
            e.getMessage();
        }
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                e.getMessage();
            }
            this.threadPool.execute(
                    new Worker(clientSocket)
            );
        }
    }
}
