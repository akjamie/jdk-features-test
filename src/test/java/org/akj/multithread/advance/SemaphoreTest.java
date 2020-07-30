package org.akj.multithread.advance;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        final SemaphoreLock lock = new SemaphoreLock();
        for(int i = 0; i < 5; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(String.format("%s - starting to get lock...", Thread.currentThread().getName()));
                        lock.lock();
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(String.format("%s - task execution complete..", Thread.currentThread().getName()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        System.out.println(String.format("%s - released lock..", Thread.currentThread().getName()));
                    }

                }
            }).start();
        }

    }

    static class SemaphoreLock{
        private Semaphore semaphore = new Semaphore(2);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock(){
            semaphore.release();
        }
    }
}
