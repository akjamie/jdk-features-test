package org.akj.multithread.basic.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
class SimpleThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(5, 20, 50, SimpleThreadPool.DEFAULT_DISCARD_POLICY);
        simpleThreadPool.start();

        IntStream.range(0, 100).forEach(i -> {
            try {
                simpleThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        log.debug("The Runnable task [" + i + "] is being serviced by " + Thread.currentThread());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        log.debug("The Runnable task [" + i + "] is finished");
                    }
                });
            } catch (SimpleThreadPool.DiscardException e) {
                log.error(e.getMessage());
            }
        });

        Thread.sleep(10000);
        simpleThreadPool.shutdown();
        simpleThreadPool.submit(() -> System.out.println("test task..."));
    }
}