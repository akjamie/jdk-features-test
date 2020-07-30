package org.akj.multithread.advance;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReEntrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(false);

        //1.test lock & show the monitor figures
        IntStream.range(0, 5).forEach(i -> new Thread(new TaskRunnable(lock)).start());

        AtomicInteger counter = new AtomicInteger(0);
        while (counter.get() <= 15) {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("hold count:" + lock.getHoldCount());
            System.out.println("waiting queue length:" + lock.getQueueLength());
            System.out.println("---------------------\n");
            counter.getAndSet(counter.get() + 1);
        }
    }

    static class TaskRunnable implements Runnable {
        private ReentrantLock lock;
        private Random random = new Random(System.currentTimeMillis());

        public TaskRunnable(ReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                final String name = Thread.currentThread().getName();
                lock.lock();
                System.out.println("==================" + name + "==================");
                System.out.println(name + " hold count:" + lock.getHoldCount());
                System.out.println(name + " waiting queue length:" + lock.getQueueLength());
                System.out.println(name + " is locked:" + lock.isLocked());
                System.out.println(name + " is held by current thread:" + lock.isHeldByCurrentThread());
                Optional.of(String.format("%s obtained the lock and start to do work..\n", Thread.currentThread().getName())).ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
