package org.akj.multithread.basic;

import java.util.concurrent.TimeUnit;

public class SynchronizedThisTest {
    public static void main(String[] args) {
        final Logger logger = new Logger();

        Thread t1 = new Thread(() -> {
            logger.add("t1 - test message");
        }, "T1");
        t1.start();

        Thread t2 = new Thread(() -> {
            logger.print("t2 - test message");
        }, "T2");
        t2.start();

        new Thread("T3"){
            @Override
            public void run() {
                logger.printNow("t3 - test message");
            }
        }.start();
    }
}

class Logger {
    private static final Object LOCK = new Object();

    public synchronized void add(String message) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void print(String str) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printNow(String str) {
        synchronized (LOCK) {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
