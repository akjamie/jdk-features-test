package org.akj.multithread.advance;

import lombok.SneakyThrows;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

public class ReentrantReadWriteLock {
    public static void main(String[] args) {
        final java.util.concurrent.locks.ReentrantReadWriteLock readWriteLock = new java.util.concurrent.locks.ReentrantReadWriteLock(true);
        final Lock readLock = readWriteLock.readLock();
        final Lock writeLock = readWriteLock.writeLock();

        final List<String> data = Collections.synchronizedList(new ArrayList<>());


        // start two read lock
        IntStream.range(0, 5).forEach((i) -> {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    final String name = Thread.currentThread().getName();
                    writeLock.lock();
                    System.out.println(name + " - obtained write lock, start to write..");

                    data.add(System.currentTimeMillis() + "");
                    TimeUnit.SECONDS.sleep(3);

                    System.out.println(name + " released write lock");
                    writeLock.unlock();
                }
            }).start();
        });


        // read lock
        IntStream.range(0, 3).forEach((i) -> {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    final String name = Thread.currentThread().getName();
                    readLock.lock();
                    System.out.println(name + " - obtained read lock, start to read..");
                    try {
                        while (true) {
//                            if (data.size() > 0) {
                            System.out.println("=============" + name + "=============");
                            data.stream().forEach(i -> {
                                System.out.print(i + " ");
                            });
                            System.out.println();
                            TimeUnit.MILLISECONDS.sleep(100);
//                            }
                        }
                    } finally {
                        readLock.unlock();
                        System.out.println(name + " released read lock");
                    }
                }
            }).start();
        });
    }
}
