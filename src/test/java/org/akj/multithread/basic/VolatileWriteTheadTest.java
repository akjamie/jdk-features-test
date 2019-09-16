package org.akj.multithread.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileWriteTheadTest {
    private static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 50;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                INIT_VALUE++;
                log.debug("INIT_VALUE is updated to {}", INIT_VALUE);
            }
        }, "update-thread-1").start();

        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                while (INIT_VALUE < MAX_VALUE) {
                    INIT_VALUE++;
                    log.debug("INIT_VALUE is updated to {}", INIT_VALUE);
                }
            }
        }, "update-thread-2").start();
    }
}
