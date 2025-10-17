package ru.netology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            log.info("Handling client {}", socket.getRemoteSocketAddress());
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            log.error("Error handling client", e);
        }
    }
}
