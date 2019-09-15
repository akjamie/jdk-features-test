package org.akj.multithread.basic.lock;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface Lock {

    public void lock() throws InterruptedException;

    public void lock(long mills) throws InterruptedException, TimeoutException;

    public void unlock();

    Collection<Thread> getBlockedThread();
}
