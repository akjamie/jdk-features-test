package org.akj.multithread.unsafe;

import sun.misc.Unsafe;

public class CASCounter implements Counter {
    private volatile long counter = 0;

    private Unsafe unsafe;

    private long offset;

    public CASCounter() throws NoSuchFieldException, IllegalAccessException {
        unsafe = UnsafeUtils.getUnsafe();
        offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
    }

    @Override
    public void increment() {
        long current = counter;
        while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
            current = counter;
        }
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
