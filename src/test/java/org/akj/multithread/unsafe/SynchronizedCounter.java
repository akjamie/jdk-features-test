package org.akj.multithread.unsafe;

public class SynchronizedCounter implements Counter {

    private long counter;

    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
