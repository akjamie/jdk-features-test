package org.akj.multithread.unsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedCounter implements Counter {

    private long counter = 0l;
    private static final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        try {
            lock.lock();
            counter++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
