package org.akj.multithread.pattern.twophasetermination;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer extends Thread {
    private int port;

    private volatile boolean terminated = false;

    private ServerSocket serverSocket;

    private List<Thread> handlers = new ArrayList<>();

    private final static int DEFAULT_PORT = 12580;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (!this.terminated) {
                Socket socket = serverSocket.accept();
                RequestHandler handler = new RequestHandler(socket);
                executorService.submit(handler);
                handlers.add(handler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.destory();
        }
    }

    private void destory() {
        this.handlers.parallelStream().forEach(i -> {
            RequestHandler handler = (RequestHandler) i;
            handler.close();
        });

        executorService.shutdown();
    }

    public void close() throws IOException {
        this.terminated = true;
        this.interrupt();
        this.serverSocket.close();
    }
}
