package ru.netology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String requestLine = reader.readLine(); // читаем первую строку запроса
            if (requestLine != null) {
                Request request = new Request(requestLine.split(" ")[1]);
                log.info("Path: {}", request.getPath());
                log.info("Query params: {}", request.getQueryParams());
            }
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            log.error("Error handling client", e);
        }
    }
}
