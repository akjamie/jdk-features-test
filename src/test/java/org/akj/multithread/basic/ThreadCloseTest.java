package org.akj.multithread.basic;

import java.util.concurrent.TimeUnit;

public class ThreadCloseTest {
    public static void main(String[] args) throws InterruptedException {
        //Worker worker = new Worker();
        WorkerNew worker = new WorkerNew();
        worker.start();

        Thread.sleep(5000);

        //worker.shutdown();
        worker.interrupt();
        System.out.println(worker.isInterrupted());
        System.out.printf("thread %s has been closed", worker.getName());
    }

    static class Worker extends Thread {
        volatile boolean active = true;

        @Override
        public void run() {
            int counter = 0;
            while (active) {
                // TODO logic here
                try {
                    System.out.println(counter++);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown() {
            this.active = false;
        }
    }

    static class WorkerNew extends Thread {
        @Override
        public void run() {
            int counter = 0;
            while (true) {
                if (isInterrupted()) {
                    break;
                }
                System.out.println(counter++);
            }
        }
    }
}
