package org.akj.multithread.basic.executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class FutureTest {

    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        Future<Double> price = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("return price info");
            return 129.50;
        });

        executorService.submit(() -> {
            System.out.println("test task....");
        });

        int indicator = 0;
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(500);
                if (price.isDone()) {
                    System.out.println("price obtained:" + price.get());
                    break;
                } else {
                    System.out.println(indicator);
                }

                indicator++;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInteruptByMainThread() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("return price info");
            return 129.50;
        });

        executorService.submit(() -> {
            System.out.println("test task....");
        });

        Thread callerThread = Thread.currentThread();

        // use MainThread to interupt the whole active threads
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callerThread.interrupt();
        }).start();

        System.out.println("price obtained:" + price.get());
    }

    @Test
    public void testTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);

            System.out.println("return price info");

            return 129.50;
        });

        executorService.submit(() -> {
            System.out.println("test task....");
        });

        Double priceValue = price.get(2, TimeUnit.SECONDS);
        System.out.println("price obtained:" + priceValue);

        Thread.sleep(10000);
    }

    @Test
    public void testIsDone() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);

            System.out.println("return price info");

            return 129.50;
        });

        Assertions.assertEquals(false, price.isDone());
    }

    @Test
    public void testIsDoneWithRuntimeException() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            throw new RuntimeException();
        });

        try {
            Double result = price.get();
            System.out.println("price obtained is: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(true, price.isDone());
    }

    @Test
    public void testCancel() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);

            System.out.println("return price info");

            return 129.50;
        });

        boolean cancel = price.cancel(true);
        if (cancel) {
            System.out.println("task has been cancelled:" + price);
        } else {
            System.out.println("task cannot been cancelled:" + price);
        }
    }

    @Test
    public void testCancelFailedWithTaskAlreadyCompleted() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            return 129.50;
        });

        Double result = price.get();
        System.out.println("price obtained is: " + result);

        boolean cancel = price.cancel(true);
        if (cancel) {
            System.out.println("task has been cancelled:" + price);
        } else {
            System.out.println("task cannot been cancelled:" + price);
        }
    }

    @Test
    public void testCancelFailedWithTaskAlreadyCancelled() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 129.50;
        });

        Thread.sleep(3);
        Assertions.assertEquals(true, price.cancel(true));
        Assertions.assertEquals(false, price.cancel(true));

        Assertions.assertEquals(true, price.isCancelled());
        Assertions.assertEquals(true, price.isDone());
    }

    @Test
    public void testCancelX() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Double> price = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("============");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-------------");
            return 129.50;
        });

        Thread.sleep(3);
        Assertions.assertEquals(true, price.cancel(true));
        Assertions.assertEquals(true, price.isCancelled());
        Assertions.assertEquals(true, price.isDone());
    }
}
