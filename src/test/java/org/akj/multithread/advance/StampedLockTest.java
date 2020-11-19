package org.akj.multithread.advance;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StampedLockTest {
    public static void main(String[] args) {
        final StampedLock lock = new StampedLock();
        final List<String> data = Collections.synchronizedList(new ArrayList<>());

        // read lock
       /* IntStream.range(0, 10).forEach((i) -> {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    final String name = Thread.currentThread().getName();
                    long stamp = -1;
                    try {
                        stamp = lock.readLock();

                        System.out.println(name + " - obtained read lock, start to read..");
                        Optional.of(data.stream().map(String::valueOf).collect(Collectors.joining("-", "'R=='", ""))).ifPresent(System.out::println);
                        TimeUnit.SECONDS.sleep(3);
                    } finally {
                        System.out.println(name + " released read lock");
                        lock.unlockRead(stamp);
                    }
                }
            }).start();
        });*/

        // read lock - optimistic
        IntStream.range(0, 10).forEach((i) -> {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    final String name = Thread.currentThread().getName();
                    long stamp = lock.tryOptimisticRead();
                    while (lock.validate(stamp)) {
                        try {
                            stamp = lock.readLock();

                            System.out.println(name + " - obtained read lock, start to read..");
                            Optional.of(data.stream().map(String::valueOf).collect(Collectors.joining("-", "'R=='", ""))).ifPresent(System.out::println);
                            TimeUnit.SECONDS.sleep(3);
                        } finally {
                            System.out.println(name + " released read lock");
                            lock.unlockRead(stamp);
                        }
                    }
                }
            }).start();
        });

        // write lock
        IntStream.range(0, 10).forEach((i) -> {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    final String name = Thread.currentThread().getName();
                    long stamp = -1;
                    try {
                        stamp = lock.writeLock();

                        System.out.println(name + " - obtained write lock, start to write..");
                        data.add(System.currentTimeMillis() + "");
                        TimeUnit.SECONDS.sleep(1);
                    } finally {
                        System.out.println(name + " released write lock");
                        lock.unlockWrite(stamp);
                    }
                }
            }).start();
        });
    }
}
