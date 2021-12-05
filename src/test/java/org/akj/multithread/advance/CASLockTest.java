package org.akj.multithread.advance;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class CASLockTest {

    public static void main(String[] args) {
        AtomicBoolean lockIndicator = new AtomicBoolean(false);
        Thread lockHolder = null;
        IntStream.of(1, 2, 3).forEach(i -> {
            new Thread(new Worker(lockIndicator, lockHolder)).start();
        });

    }

    static class Worker implements Runnable {
        private static AtomicBoolean lockIndicator;

        private static Thread lockHolder;

        Worker(AtomicBoolean lock, Thread holder) {
            lockIndicator = lock;
            lockHolder = holder;
        }

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println(String.format("Thread: %s, locked: %s", thread.getName(),
                    thread == lockHolder ? lockIndicator : false));
            if (lockIndicator.compareAndSet(false, true)) {
                System.out.println(String.format("%s got lock", thread.getName()));
                //set thread holder
                lockHolder = thread;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockIndicator.set(false);
                lockHolder = null;
            } else {
                System.out.println(String.format("%s retrying to get lock", thread.getName()));
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }
        }
    }
}
