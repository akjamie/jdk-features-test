package org.akj.multithread.pattern.readwritelock;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class WriteWorker extends Thread {
    private final static SecureRandom random = new SecureRandom();

    private final SharedData sharedData;

    public WriteWorker(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public WriteWorker(SharedData sharedData, String threadName) {
        this.sharedData = sharedData;
        this.setName(threadName);
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] sharedSecret = new byte[32];
                random.nextBytes(sharedSecret);
                String token = Base64.getEncoder().encodeToString(sharedSecret);
                sharedData.write(token.toCharArray());
                log.debug("Thread-{} write token: {}", Thread.currentThread().getName(), token);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

