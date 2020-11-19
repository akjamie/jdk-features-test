package org.akj.multithread.advance;

import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveActionTest {
    private final static AtomicInteger sum = new AtomicInteger(0);

    @SneakyThrows
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> forkJoinTask = forkJoinPool.submit(new CalculateRecursiveAction(0, 100));

        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("result: " + sum);
    }

    static class CalculateRecursiveAction extends RecursiveAction {
        private int start;
        private int end;
        private int thredhold = 5;

        CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= thredhold) {
                sum.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {

                int mid = (start + end) / 2;
                CalculateRecursiveAction leftTask = new CalculateRecursiveAction(start, mid);
                leftTask.fork();

                CalculateRecursiveAction rightTask = new CalculateRecursiveAction(mid + 1, end);
                rightTask.fork();
            }
        }
    }
}
