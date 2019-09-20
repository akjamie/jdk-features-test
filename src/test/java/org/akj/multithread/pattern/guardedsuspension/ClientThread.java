package org.akj.multithread.pattern.guardedsuspension;

public class ClientThread extends Thread {
    private final RequestQueue<BaseRequest> queue;

    public ClientThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        //FIXME test logics
        for (int i = 0; i < 100000; i++) {
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setPayload("Test message from client " + (i + 1));
            queue.put(baseRequest);
        }
    }
}
