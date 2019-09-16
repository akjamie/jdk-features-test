package org.akj.multithread.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolatileTest {
    private static int INIT_VALUE = 0;
    private final static int MAX_VALUE = 50;

    public static void main(String[] args) {
        /**
         * no write operation, so JVM takes it as read only thread and will not sync INIT_VALUE always, that's why we
         * see below thread is always live
         */
        new Thread(() -> {
            int localValue = INIT_VALUE;

            while (localValue < MAX_VALUE) {
                if (localValue != INIT_VALUE) {
                    log.debug("the local is updated to {}", INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "readonly-thread").start();

        new Thread(() -> {
            int localValue = INIT_VALUE;

            while (INIT_VALUE < MAX_VALUE) {
                log.debug("the INIT_VALUE is updated to {}", ++localValue);
                INIT_VALUE = localValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "update-thread").start();
    }
}
