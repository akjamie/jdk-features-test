package org.akj.multithread.basic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-1");
        t1.setPriority(Thread.MAX_PRIORITY);

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-2");
        t2.setPriority(Thread.NORM_PRIORITY);

        Thread t3 = new Thread(() -> {
            while (true) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-3");
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }

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

    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
                Optional.of("testing message").ifPresent(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "test-thread");

        t1.start();
        t1.join();

        System.out.println(t1.getName());
        System.out.println(t1.getId());
        System.out.println(t1.getPriority());
    }

    @Test
    public void test2() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-1");
        t1.setPriority(Thread.MAX_PRIORITY);

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-2");
        t2.setPriority(Thread.NORM_PRIORITY);

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("test message from thread:" + Thread.currentThread().getName());
            }
        }, "test-thread-3");
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }
}
