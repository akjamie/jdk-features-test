package org.akj.multithread.advance;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class CompletableFutureTest {

    @Test
    public void test() {
        List<Integer> results = new ArrayList<>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.prestartAllCoreThreads();
        Random generator = new Random(1000);

        IntStream.range(0, 19).parallel().forEach(i ->
                executor.submit(() -> {
                    results.add(generator.nextInt());
                })
        );

        System.out.println(results.size());
        executor.shutdown();
    }

    @RepeatedTest(5)
    public void test1() {
        List<Integer> results = new ArrayList<>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5));
        executor.prestartAllCoreThreads();
        Random generator = new Random(1000);

        IntStream.range(0, 19).parallel().forEach(i ->
                executor.submit(() -> {
                    results.add(generator.nextInt());
                })
        );

        System.out.println(results.size());
        executor.shutdown();
    }

    @RepeatedTest(50)
    public void test2() throws InterruptedException {
        List<Integer> results = new ArrayList<>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.prestartAllCoreThreads();
        Random generator = new Random(1000);
        AtomicInteger atomicInteger = new AtomicInteger(1);

        IntStream.range(0, 20).parallel().forEach(i ->
                {
                    Future future = executor.submit(() -> {
                        results.add(generator.nextInt());
                        atomicInteger.addAndGet(1);
                    });
                }
        );

        while (atomicInteger.get() <= 20) {
            System.out.println("main thread is blocking to wait for the async result.");
            Thread.currentThread().sleep(300);
        }
        System.out.println(results.size());
        System.out.println(results.size());
        executor.shutdown();
    }

    @Test
    public void test3() throws InterruptedException {
        List<Integer> results = new ArrayList<>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        executor.prestartAllCoreThreads();
        Random generator = new Random(1000);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        List<CompletableFuture> completableFutures = new ArrayList<>();

        IntStream.range(0, 20).parallel().forEach(i -> {
                    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync((() -> {
                        int get = atomicInteger.addAndGet(1);
                        if (get == 10) {
                            throw new IllegalStateException("mocked exception.");
                        }
                        results.add(generator.nextInt(1000));
                    }), executor).exceptionally(err -> {
                        System.out.println(err.getMessage());
                        return null;
                    });
                    completableFutures.add(completableFuture);
                }
        );
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[]{})).join();

        System.out.println("result size: " + results.size());
        results.stream().forEach(System.out::println);
        executor.shutdown();
    }

    @Test
    public void test4() throws InterruptedException {
        List<Integer> results = new ArrayList<>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        CompletionService completionService = new ExecutorCompletionService(executor);
        Random generator = new Random(1000);

        IntStream.range(0, 20).parallel().forEach(i ->
                completionService.submit(() -> {
                    results.add(generator.nextInt(1000));
                },null)
        );
        int count = 0;

        while (count <= 19) {
            if (completionService.poll() != null) {
                System.out.println("waiting, count: " + count);
                count++;
            }
        }
        System.out.println("result size: " + results.size());
        results.stream().forEach(System.out::println);
        executor.shutdown();
    }

    @SneakyThrows
    @Test
    public void test5(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            try {
                log.info("{} start to process.", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
                log.info("{} is executing.", Thread.currentThread().getName());
                completableFuture.complete("success.");
                log.info("{} process finished.", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        log.info("{} - handle result: {}", Thread.currentThread().getName(), completableFuture.get());
    }

    @SneakyThrows
    @Test
    public void test6(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                log.info("{} start to process.", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
                log.info("{} is executing.", Thread.currentThread().getName());
                log.info("{} process finished.", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        future.get();

        log.info("{} - DONE", Thread.currentThread().getName());
    }

}
