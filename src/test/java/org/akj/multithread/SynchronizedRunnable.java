package org.akj.multithread;

public class SynchronizedRunnable implements Runnable {
    private static final int MAX = 500;
    private volatile Integer index = 0;
    private boolean finished = false;

    @Override
    public void run() {
        while (!finished) {
            Integer no = ticket();

            if(null != no){
                System.out.println(Thread.currentThread().getName() + " - No." + String.format("%03d", no));
            }
        }
    }

    private synchronized Integer ticket() {
        if(index > MAX){
            finished = true;
            return null;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return index++;
    }
}
