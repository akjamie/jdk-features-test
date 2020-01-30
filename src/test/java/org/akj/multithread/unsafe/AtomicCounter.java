package org.akj.multithread.unsafe;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {
    private AtomicLong counter = new AtomicLong();
    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public long getCounter() {
        return counter.get();
    }
}
