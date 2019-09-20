package org.akj.multithread.pattern.countdown;

public class CustomCountDownLatch {

    private int total;

    private int counter = 0;

    public CustomCountDownLatch(int count) {
        this.total = count;
    }

    public void countDown() {
        synchronized (this) {
            this.counter++;
            this.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this){
            while(this.counter != this.total){
                this.wait();
            }
        }
    }
}
