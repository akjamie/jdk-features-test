package org.akj.multithread.advance;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionTraditionalTest {
    private static volatile boolean useIndicator = true;
    private static volatile Integer counter = 0;
    private static final Object MONITOR = new Object();

    public static void main(String[] args) {
        IntStream.range(0, 3).forEach(i -> new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    produce();
                }
            }
        }).start());

        IntStream.range(0, 5).forEach(i -> new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    consume();
                }
            }
        }).start());
    }

    @SneakyThrows
    private static void produce() {
        synchronized (MONITOR) {
            while (!useIndicator) {
                MONITOR.wait();
            }

            TimeUnit.MILLISECONDS.sleep(200);
            counter++;
            System.out.println(String.format("%s - produced data: %d", Thread.currentThread().getName(), counter));
            useIndicator = false;
            MONITOR.notifyAll();
        }
    }

    @SneakyThrows
    private static void consume() {
        synchronized (MONITOR) {
            while (useIndicator) {
                MONITOR.wait();
            }

            TimeUnit.SECONDS.sleep(1);
            System.out.println(String.format("%s - consumed data: %d", Thread.currentThread().getName(), counter));
            useIndicator = true;
            counter--;
            MONITOR.notifyAll();
        }
    }
}
