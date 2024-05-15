package ru.ivanov.march.chat;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.Socket;

public class Worker implements Runnable {

    public Socket clientSocket;
    public Dispatcher dispatcher;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.dispatcher = new Dispatcher();
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[8192];
            System.out.println("worker started" + clientSocket);
            int n = clientSocket.getInputStream().read(buffer);
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.getMessage();
        }


    }
}
