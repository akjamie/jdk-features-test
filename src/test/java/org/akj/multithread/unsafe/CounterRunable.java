package org.akj.multithread.unsafe;

public class CounterRunable implements Runnable {
    private Counter counter;

    private int num = 0;

    public CounterRunable(Counter counter, int num) {
        this.counter = counter;
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 0; i < num; i++) {
            counter.increment();
        }
    }
}
