package org.akj.multithread.basic;

public class ThreadInterruptTest {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {
//        test();
        test1();
    }

    private static void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.interrupted());
                }
            }
        }, "test-thread-1");

        t1.start();
        Thread.sleep(100);

        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (MONITOR) {
                    try {
                        MONITOR.wait(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.interrupted());
                    }
                }
            }
        }, "test-thread-1");

        t1.start();
        //Thread.sleep(100);

        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }
}
