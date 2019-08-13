package org.akj.multithread;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    @Test
    public void test() {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread name: " + Thread.currentThread().getName());
        }, "test-thread-0");
        t.start();

        System.out.println("Thread group of " + t + " is: " + t.getThreadGroup());

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println("Thread count in thread group is: " + threadGroup.activeCount());
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);

        Arrays.asList(threads).forEach(System.out::println);
    }
}
