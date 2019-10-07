package org.akj.multithread.pattern.observer.usercase;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DataService implements LifeCycleListener {
    private final static Object LOCK = new Object();

    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            log.debug("the state of Thead {} has changed, the new state is {}", event.getThread().getName(),
                    event.getState());
            if (event.getState() == ObservableRunnable.RunnableState.FAILED || null != event.getCause()) {
                log.error("the query of {} is failed, root cause is {}, added to exception queue for retry",
                        ((DataServiceRunnableEvent) event).getTaskId(), event.getCause().getMessage());
            }
            if (event.getState() == ObservableRunnable.RunnableState.SUCCEED) {
                log.debug("query of {} is finished", ((DataServiceRunnableEvent) event).getTaskId());
            }
        }
    }

    public void query(List<String> ids) {
        if (ids == null || ids.isEmpty()) return;

        ids.parallelStream().forEach(id -> {
            new Thread(new ObservableRunnable(this) {
                @Override
                public void run() {
                    log.debug("start to exec the query for id: {}", id);
                    try {
                        notify(new DataServiceRunnableEvent(id, RunnableState.RUNNING, Thread.currentThread(), null));
                        Thread.sleep(1000);
                        simulateError(0.2);
                        notify(new DataServiceRunnableEvent(id, RunnableState.SUCCEED, Thread.currentThread(), null));
                    } catch (Exception e) {
                        notify(new DataServiceRunnableEvent(id, RunnableState.FAILED, Thread.currentThread(), e));
                    }
                }
            }, id).start();
        });
    }

    private void simulateError(double rate) {
        int seed = 10;

        if (rate < 0 || rate > 1) {
            log.debug("wrong error rate, will be ignore and use default error rate 10%");
            rate = 0.1;
        }

        int temp = (int) Math.round(seed * rate);
        int gen = (int) (Math.random() * seed);
        log.info("generate random value for error simulation is {}", gen);
        if (gen <= temp) throw new RuntimeException("simulated error for testing");
    }
}
