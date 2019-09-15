package org.akj.multithread.basic.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class BooleanLockTest {
    private static Logger log = LoggerFactory.getLogger(BooleanLockTest.class);

    public static void main(String[] args) {
        final BooleanLock booleanLock = new BooleanLock();
        List<Thread> threads = new ArrayList<>();
        Stream.of("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T0").forEach(name -> {
            Thread t = new Thread(() -> {
                try {
                    log.debug(Thread.currentThread().getName() + " is created...");
                    booleanLock.lock();
                    log.debug(Thread.currentThread().getName() + " got the lock monitor...");

                    work();
                    log.debug(Thread.currentThread().getName() + " completed...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    booleanLock.unlock();
                }
            }, name);
            t.start();

            threads.add(t);
        });

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.debug("all workers have finished, process exited...");
    }

    public static void work() throws InterruptedException {
        log.debug(Thread.currentThread().getName() + " is running...");
        Thread.sleep(5_000);
    }
}