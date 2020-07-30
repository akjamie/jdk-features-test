package org.akj.multithread.advance;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReEntrantLockWithTimeoutTest {
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(false);

        //test lock timeout and failed to get lock
        //IntStream.range(0, 2).forEach(i -> new Thread(new TaskRunnable(lock)).start());
        IntStream.range(0, 3).forEach(i -> new Thread(new TaskRunnable(lock, 10000)).start());
    }

    static class TaskRunnable implements Runnable {
        private ReentrantLock lock;
        private Random random = new Random(System.currentTimeMillis());
        private int timeout = -1;

        public TaskRunnable(ReentrantLock lock) {
            this.lock = lock;
        }

        public TaskRunnable(ReentrantLock lock, int timeout) {
            this.lock = lock;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            try {
                final String name = Thread.currentThread().getName();
                final long baseTime = System.currentTimeMillis();
                if (timeout == -1) {
                    lock.lock();
                    Optional.of(String.format("%s obtained the lock and start to do work..\n", Thread.currentThread().getName())).ifPresent(System.out::println);
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } else {
                    // if not timeout, then continue try to get lock
                    while (baseTime + timeout >= System.currentTimeMillis()) {
                        boolean result = lock.tryLock();
                        if (result) {
                            System.out.println(String.format("%s - get lock successfully", name));
                            TimeUnit.MILLISECONDS.sleep(500);
                            break;
                        }

                        System.out.println(String.format("%s - get lock failed, wait for other thread release lock..", name));
                    }

                    // no lock obtained and timeout reached
                    if (!lock.isHeldByCurrentThread()) {
                        throw new RuntimeException(name + " get lock failed..");
                    }
                    Optional.of(String.format("%s obtained the lock and start to do work..\n", name)).ifPresent(System.out::println);
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) lock.unlock();
            }
        }
    }
}
