package org.akj.multithread.advance;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String exchange = exchanger.exchange(Thread.currentThread().getName() + " - Hello, nice to meet you");
                    System.out.println(Thread.currentThread().getName() + " Get reply message: " + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread-01").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    String exchange = exchanger.exchange(Thread.currentThread().getName() + " - Hi, nice to meet you too");
                    System.out.println(Thread.currentThread().getName() + " Get message:" + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread-02").start();
    }
}
