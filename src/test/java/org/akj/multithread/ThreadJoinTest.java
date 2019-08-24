package org.akj.multithread;

import java.util.stream.IntStream;

public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        }, "test-thread-1");

        Thread t2 = new Thread(() -> {
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        }, "test-thread-2");

        t1.start();
        t1.join(50);

        t2.start();
        t2.join(50);

        System.out.println("all sub threads have been completed");
    }

    public static void test1(){

    }
}
