package org.akj.multithread.advance;

import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest {
    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("all sub tasks have been finished..");
            }
        });

        new Thread(new TaskRunnable(cyclicBarrier)).start();
        new Thread(new TaskRunnable(cyclicBarrier)).start();
        new Thread(new TaskRunnable(cyclicBarrier)).start();
        new Thread(new TaskRunnable(cyclicBarrier)).start();
        new Thread(new TaskRunnable(cyclicBarrier)).start();


        while(true){
            counter.incrementAndGet();
            //TimeUnit.MILLISECONDS.sleep(100);
            //TimeUnit.MILLISECONDS.sleep(100);
            System.out.println(String.format("=======check task run status[%d]=========",counter.get()));
            System.out.println(String.format("Waiting task number: %d", cyclicBarrier.getNumberWaiting()));
            System.out.println(String.format("Parties number: %d", cyclicBarrier.getParties()));
            System.out.println(String.format("isBroken: %s\n", cyclicBarrier.isBroken()));
            if(cyclicBarrier.getNumberWaiting() == 0){
                break;
            }
        }
    }

    static class TaskRunnable implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private Random random = new Random(System.currentTimeMillis());

        public TaskRunnable(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(10));
                System.out.println(String.format("%s is waiting for other task to complete.", Thread.currentThread().getName()));
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
