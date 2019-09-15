package org.akj.multithread.basic.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class SimpleThreadPool extends Thread {
    private int size;

    private int minSize;

    private int maxSize;

    private final int queueSize;

    private final DiscardPolicy discardPolicy;

    private volatile boolean destroyed = false;

    private final static int DEFAULT_POOL_SIZE = 5;

    private final static int DEFAULT_TASK_QUEUE_SIZE = 1000;

    private final static LinkedList<Runnable> taskQueue = new LinkedList<Runnable>();

    private final static ThreadGroup threadgroup = new ThreadGroup("Thread-pool");

    private final static String workerThreadNamePrefix = "thread-poo1-";

    private final static List<WorkerThread> threadPool = new ArrayList<>();

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("total number of tasks in queue has been reached the max queue size, new task is " +
                "rejected");
    };

    public SimpleThreadPool() {
        this(DEFAULT_POOL_SIZE, DEFAULT_POOL_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
        init();
    }

    public SimpleThreadPool(int poolSize) {
        this(poolSize, poolSize, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
        init();
    }

    public SimpleThreadPool(int coreSize, int maxSize, int queueSize, DiscardPolicy discardPolicy) {
        this.size = coreSize;
        this.minSize = coreSize;
        this.maxSize = maxSize;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        IntStream.range(0, this.size).forEach(i -> {
            WorkerThread workerThread = createWorkerThread(i);
            threadPool.add(workerThread);
        });
    }

    private WorkerThread createWorkerThread(int i) {
        WorkerThread workerThread = new WorkerThread(threadgroup, workerThreadNamePrefix + i);
        workerThread.start();
        return workerThread;
    }

    @Override
    public void run() {
        while (!destroyed) {
            log.debug("[thread-pool] core pool size: {}, max pool size: {}, threads in thread group: {}, tasks in " +
                    "queue: {} \n", this.size, this.maxSize, threadPool.size(), taskQueue.size());
            try {
                Thread.sleep(200);
                if (taskQueue.size() > maxSize && size < maxSize) {
                    for (int i = size; i < maxSize; i++) {
                        WorkerThread workerThread = createWorkerThread(i);
                        threadPool.add(workerThread);
                    }
                    this.size = maxSize;
                    log.debug("[thread pool] the thread pool size increased to {}", this.size);
                }

                synchronized (threadPool) {
                    if (taskQueue.isEmpty() && this.size >= this.maxSize) {
                        int reduceSize = this.maxSize - (this.minSize <= this.size ? this.minSize : this.size);
                        if ((this.minSize != threadPool.size())) {
                            reduceSize = threadPool.size() - this.minSize;

                            for (Iterator<WorkerThread> it = threadPool.iterator(); it.hasNext(); ) {
                                if (reduceSize <= 0) break;

                                WorkerThread wt = it.next();
                                if (wt.state != WorkerState.RUNNING) {

                                    wt.interrupt();
                                    wt.close();
                                    it.remove();
                                    reduceSize--;
                                    log.debug("destroy worker thread: " + wt.getName());
                                }
                            }

                            if (reduceSize == 0) {
                                this.size = this.minSize;
                                log.debug("[thread pool] the thread pool size decreased to {}", this.size);
                            }

                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void submit(Runnable runnable) {
        if (this.destroyed)
            throw new IllegalStateException("thread pool has already been disposed, task submission is " +
                    "not allowed");
        synchronized (taskQueue) {
            if (taskQueue.size() >= queueSize) discardPolicy.discard();
            taskQueue.addLast(runnable);
            taskQueue.notifyAll();
        }
    }

    public void shutdown() throws InterruptedException {
        log.debug("start to shutdown thread pool");
        while (!taskQueue.isEmpty()) {
            Thread.sleep(50);
        }

        synchronized (taskQueue) {
            int threadSize = threadPool.size();
            while (threadSize > 0) {
                for (WorkerThread wt : threadPool) {
                    if (wt.state == WorkerState.BLOCKED) {
                        wt.interrupt();
                        wt.close();

                        threadSize--;
                    } else if (wt.state == WorkerState.READY) {
                        wt.close();
                        threadSize--;
                    } else {
                        Thread.sleep(50);
                    }
                }
            }
        }

        int activeCount = threadgroup.activeCount();
        log.debug("active thread count in thread pool: {}", activeCount);
        while (activeCount > 0) {
            Thread.sleep(100);
        }

        this.destroyed = true;
        log.debug("succeed shutdown thread pool");
    }

    private static class WorkerThread extends Thread {
        private volatile WorkerState state = WorkerState.READY;

        public WorkerThread(ThreadGroup group, String name) {
            super(group, name);
            this.state = WorkerState.READY;
        }

        public WorkerState getWorkerState() {
            return this.state;
        }

        @Override
        public void run() {
            OUTER:
            while (this.state != WorkerState.DEAD) {
                Runnable runnable = null;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            this.state = WorkerState.BLOCKED;
                            taskQueue.wait(100);
                        } catch (InterruptedException e) {
                            log.error("worker thread {} is interrupted", Thread.currentThread().getName());
                            break OUTER;
                        }
                    }
                    runnable = taskQueue.removeFirst();
                }

                if (null != runnable) {
                    this.state = WorkerState.RUNNING;
                    runnable.run();
                    this.state = WorkerState.READY;
                }
            }
        }

        public void close() {
            this.state = WorkerState.DEAD;
        }
    }

    private enum WorkerState {
        READY, BLOCKED, RUNNING, DEAD
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
