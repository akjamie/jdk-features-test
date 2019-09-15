package org.akj.multithread.basic.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Slf4j
public class BooleanLock implements Lock {
    // initial value, false - not hold by anyone, true - hold by thread
    private boolean lockIndicator = false;

    private HashMap<String, Thread> blockedThreadList = new HashMap<>();

    private Thread ownerThread = null;

    @Override
    public synchronized void lock() throws InterruptedException {
        while (lockIndicator) {
            blockedThreadList.put(Thread.currentThread().getName(), Thread.currentThread());
            this.wait();
        }

        blockedThreadList.remove(Thread.currentThread().getName());
        this.lockIndicator = true;
        ownerThread = Thread.currentThread();
        log.debug(Thread.currentThread().getName() + " has the lock monitor, {} threads is waiting in the queue",
                this.blockedThreadList.size());
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeoutException {
        if(mills <= 0){
            lock();
        }

        long endTime = System.currentTimeMillis() + mills;
        long remaining = mills;
        while(lockIndicator){
            if(remaining <= 0){
                throw new TimeoutException();
            }
            blockedThreadList.put(Thread.currentThread().getName(),Thread.currentThread());
            this.wait(mills);
            remaining = endTime - System.currentTimeMillis();
        }

        blockedThreadList.remove(Thread.currentThread().getName());
        this.lockIndicator = true;
        ownerThread = Thread.currentThread();
        log.debug(Thread.currentThread().getName() + " has the lock monitor, {} threads is waiting in the queue",
                this.blockedThreadList.size());
    }

    @Override
    public synchronized void unlock() {
        if (ownerThread == Thread.currentThread()) {
            log.debug(Thread.currentThread().getName() + " released the lock monitor...");
            ownerThread = null;
            this.lockIndicator = false;
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableMap(this.blockedThreadList).values();
    }
}
