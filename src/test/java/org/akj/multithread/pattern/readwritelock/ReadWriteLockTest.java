package org.akj.multithread.pattern.readwritelock;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteLockTest {

    public static void main(String[] args) {
        SharedData sharedData = new SharedData(64);
        new ReadWorker(sharedData, "read-1").start();
        new ReadWorker(sharedData, "read-2").start();
        new ReadWorker(sharedData, "read-3").start();
        new ReadWorker(sharedData, "read-4").start();
        new ReadWorker(sharedData, "read-5").start();
        new ReadWorker(sharedData, "read-6").start();

        new WriteWorker(sharedData,"write-1").start();
        new WriteWorker(sharedData,"write-2").start();
    }
}