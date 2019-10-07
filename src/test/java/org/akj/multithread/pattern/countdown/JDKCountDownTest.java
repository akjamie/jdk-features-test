package org.akj.multithread.pattern.countdown;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Slf4j
public class JDKCountDownTest {
    private final static int threadCount = 6;

    private final static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        log.debug("start to test count down");

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount).forEach(i -> new Thread(()->{
            log.debug("{} - start execution", Thread.currentThread());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("{} - finished execution", Thread.currentThread());
            countDownLatch.countDown();
        },"test-thread-" + i).start());

        countDownLatch.await();
        log.debug("all workers are finished, program exits.");
    }
}
