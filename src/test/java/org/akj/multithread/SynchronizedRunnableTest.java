package org.akj.multithread;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedRunnableTest {

    public static void main(String[] args) {
        SynchronizedRunnable runnable = new SynchronizedRunnable();

        Thread t1 = new Thread(runnable,"Counter-01");
        Thread t2 = new Thread(runnable,"Counter-02");
        Thread t3 = new Thread(runnable,"Counter-03");

        t1.start();
        t2.start();
        t3.start();
    }
}