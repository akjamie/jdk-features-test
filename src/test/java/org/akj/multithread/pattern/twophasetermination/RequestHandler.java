package org.akj.multithread.pattern.twophasetermination;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class RequestHandler extends Thread {
    private Socket socket;

    private volatile boolean terminated = false;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             PrintWriter writer = new PrintWriter(outputStream)) {
            while (!terminated) {
                String message = reader.readLine();
                if (message == null) break;

                log.debug("message received from client > {}", message);
                writer.write("server ack message");
                writer.flush();
            }
        } catch (IOException e) {
            terminated = true;
        }
    }

    public void close() {
        if (this.terminated) return;

        try {
            this.terminated = true;
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
