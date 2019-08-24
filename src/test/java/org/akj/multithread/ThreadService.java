package org.akj.multithread;

public class ThreadService {
    private Thread execThread = null;

    private boolean active = true;

    public void execute(Runnable task) {
        execThread = new Thread() {
            @Override
            public void run() {
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();

                try {
                    runner.join();
                    active = false;
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println(e);
                }
            }
        };

        execThread.start();
    }

    public void shutdown(long mills) {
        long start = System.currentTimeMillis();
        while (active) {
            if (System.currentTimeMillis() - start >= mills) {
                System.out.println("task execution timeout");
                execThread.interrupt();
                break;
            }

            try {
                execThread.sleep(10);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("task has been interrupted");
                break;
            }
        }
        active = true;
    }
}
