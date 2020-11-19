package org.akj.multithread.advance;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinRecursiveTaskTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> result = forkJoinPool.submit(new CalculateRecursiveTask(0, 100));

        try {
            Integer integer = result.get();
            System.out.println("result: " + integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CalculateRecursiveTask extends RecursiveTask<Integer> {
        private int start;
        private int end;
        private int thredhold = 5;

        CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= thredhold) {
                return IntStream.rangeClosed(start, end).sum();
            }

            int mid = (start + end) / 2;
            CalculateRecursiveTask leftTask = new CalculateRecursiveTask(start, mid);
            leftTask.fork();

            CalculateRecursiveTask rightTask = new CalculateRecursiveTask(mid + 1, end);
            rightTask.fork();

            return leftTask.join() + rightTask.join();
        }
    }
}
