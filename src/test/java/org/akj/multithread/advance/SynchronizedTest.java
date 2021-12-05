package org.akj.multithread.advance;

public class SynchronizedTest {
    private static final String THREAD_INDICATOR = "THREAD_INDICATOR";
    private Object monitor = new Object();

    public void test(){
        synchronized (monitor){
            System.out.println("this is for testing the synchronized block with instance variable");
        }
    }

    public void test1(){
        synchronized (SynchronizedTest.class){
            System.out.println("this is for testing the synchronized block with static(class) variable");
        }
    }

    public void test2(){
        synchronized (THREAD_INDICATOR){
            System.out.println("this is for testing the synchronized block with class file");
        }
    }

    public synchronized void test3(){
        System.out.println("this is for testing the synchronized method");
    }
}
