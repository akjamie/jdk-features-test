package org.akj.multithread.pattern.guardedsuspension;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class ServerThead extends Thread {

    private volatile boolean stopped = false;

    private final RequestQueue<BaseRequest> queue;

    public ServerThead(RequestQueue<BaseRequest> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!stopped) {
            BaseRequest request = (BaseRequest) queue.get();
            if (request == null) continue;
            log.debug("receive request from client, request body is {}", request.getPayload());
        }
    }

    public void close() {
        this.stopped = true;
        this.interrupt();
    }
}
