package org.akj.multithread.advance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
    private static ExecutorService executors = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        // demo:
        //   1.query data
        //   2.multi-thread to do the calculation
        //   3.aggregate result and print
        Integer [] data = new Integer[13];
        query().toArray(data);

        CountDownLatch countDownLatch = new CountDownLatch(data.length);
        for (int i=0; i< data.length; i++) {
            executors.execute(new TaskRunnable(i, data, countDownLatch));
        }

        countDownLatch.await();

        for (int i=0; i< data.length; i++) {
            System.out.printf("%04d ", data[i]);
        }
        executors.shutdown();
    }

    public static List<Integer> query() {
        return List.of(1, 11, 21, 31, 20, 41, 51, 61, 60, 71, 81, 91, 100);
    }

    @Data
    @AllArgsConstructor
    static class TaskRunnable implements Runnable {
        private int index;

        private Integer [] data;

        private CountDownLatch countDownLatch;

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.data[index] % 4 == 0) {
                this.data[index] = data[index] + 33;
            } else {
                this.data[index] = data[index] + 7;
            }
            System.out.printf("%s has finished task..\n", Thread.currentThread().getName());
            countDownLatch.countDown();
        }
    }
}
