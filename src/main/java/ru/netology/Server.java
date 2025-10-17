package ru.netology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 64;

    private final ExecutorService threadPool;

    public Server() {
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Server started on port {}", PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("New client connected: {}", clientSocket.getRemoteSocketAddress());
                threadPool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            log.error("Server error", e);
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        threadPool.shutdown();
        log.info("Server shutdown.");
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
