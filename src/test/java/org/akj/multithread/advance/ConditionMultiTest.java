package org.akj.multithread.advance;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionMultiTest {
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition PRODUCE_CONDITION = LOCK.newCondition();
    private static final Condition CONSUME_CONDITION = LOCK.newCondition();
    private static final int MAX_SIZE = 5;
    private static final LinkedList<Long> DATA = new LinkedList<Long>();

    public static void main(String[] args) {
        IntStream.range(0, 3).forEach(i -> new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                    produce();
                }
            }
        }).start());

        IntStream.range(0, 5).forEach(i -> new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                    consume();
                }
            }
        }).start());
    }

    @SneakyThrows
    private static void produce(){
        try{
            LOCK.lock();
            while(DATA.size() >= MAX_SIZE){
                PRODUCE_CONDITION.await();
            }

            TimeUnit.MILLISECONDS.sleep(200);
            long data = System.currentTimeMillis();
            System.out.println(String.format("%s - produced data: %d", Thread.currentThread().getName(), data));
            DATA.addLast(data);

            CONSUME_CONDITION.signalAll();
        }finally {
            LOCK.unlock();
        }
    }

    @SneakyThrows
    private static void consume(){
        try{
            LOCK.lock();
            while(DATA.isEmpty()){
                CONSUME_CONDITION.await();
            }

            TimeUnit.MILLISECONDS.sleep(200);
            long data = DATA.remove();
            System.out.println(String.format("%s - consumed data: %d", Thread.currentThread().getName(), data));

            PRODUCE_CONDITION.signalAll();
        }finally {
            LOCK.unlock();
        }
    }
}
