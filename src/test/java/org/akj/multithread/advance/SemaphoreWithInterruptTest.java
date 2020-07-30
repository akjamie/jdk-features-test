package org.akj.multithread.advance;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SemaphoreWithInterruptTest {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore lock = new Semaphore(3);
        final AtomicReference<Integer> counter = new AtomicReference<>(0);
        for (int i = 0; i < 5; i++) {
            createThread(lock, 3).start();
        }

//        while (counter.get() <= 15) {
//            TimeUnit.MILLISECONDS.sleep(500);
//            System.out.println("available permits:" + lock.availablePermits());
//            System.out.println("estimated queue length:" + lock.getQueueLength());
//            System.out.println("---------------------\n");
//            counter.getAndSet(counter.get() + 1);
//        }

        // create thread
        Thread t6 = createThread(lock, -1);
        t6.start();

        // after main thread sleep 10 second, if still no lock acquired, then interrupt t6 to still waiting
        TimeUnit.SECONDS.sleep(10);
        t6.interrupt();

    }

    private static Thread createThread(Semaphore lock, int timeout) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(String.format("%s - starting to get lock...", Thread.currentThread().getName()));
                    lock.acquire(2);
                    if (timeout != -1)
                        TimeUnit.SECONDS.sleep(timeout);
                    System.out.println(String.format("%s - task execution complete..", Thread.currentThread().getName()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.release(2);
                    System.out.println(String.format("%s - released lock..", Thread.currentThread().getName()));
                }

            }
        });
    }

}
