package org.akj.multithread.unsafe;

public class StupiedCounter implements Counter {

    private long counter;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
