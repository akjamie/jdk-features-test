package org.akj.multithread.advance;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SynchronousQueueTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 2,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        IntStream.of(1, 2, 3, 4, 5).forEach(i -> {
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread id:" + Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

    }
}
