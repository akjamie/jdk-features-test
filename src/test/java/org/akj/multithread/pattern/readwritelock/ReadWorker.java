package org.akj.multithread.pattern.readwritelock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadWorker extends Thread {
    private final SharedData sharedData;

    public ReadWorker(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public ReadWorker(SharedData sharedData, String threadName) {
        this.sharedData = sharedData;
        this.setName(threadName);
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] token = sharedData.read();
                log.debug("Thread-{} read token:{}", Thread.currentThread().getName(), new String(token).trim());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
