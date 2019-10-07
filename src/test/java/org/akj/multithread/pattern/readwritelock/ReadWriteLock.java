package org.akj.multithread.pattern.readwritelock;

import lombok.extern.slf4j.Slf4j;

/**
 * Read Write Lock
 *
 * @Author Jamie Zhang
 * @Since Sep 17,2019
 */
@Slf4j
public class ReadWriteLock {

    private int readingReaders = 0;

    private int waitingReaders = 0;

    private int writingWriters = 0;

    private int waitingWriters = 0;

    private boolean preferWrite;

    public ReadWriteLock() {
        this(false);
    }

    public ReadWriteLock(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            while (this.writingWriters > 0 || (this.preferWrite && this.waitingWriters > 0 && this.waitingReaders < 20)) {
                this.wait();
            }

            this.readingReaders++;
            //log.debug("Thread {} obtained the read lock", Thread.currentThread().getName());
        } finally {
            this.waitingReaders--;
        }
    }

    public synchronized void readUnlock() {
        this.readingReaders--;
        this.notifyAll();
        //log.debug("Thread {} released the read lock", Thread.currentThread().getName());
    }

    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (this.readingReaders > 0 || this.writingWriters > 0 || this.waitingReaders > 20) {
                this.wait();
            }

            this.writingWriters++;
            //log.debug("Thread {} obtained the write lock", Thread.currentThread().getName());
        } finally {
            this.waitingWriters--;
        }
    }

    public synchronized void writeUnlock() throws InterruptedException {
        this.writingWriters--;
        this.notifyAll();
        //log.debug("Thread {} released the read lock", Thread.currentThread().getName());
    }

}
