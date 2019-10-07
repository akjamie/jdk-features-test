package org.akj.multithread.pattern.observer.usercase;

import lombok.AllArgsConstructor;
import lombok.Data;

public abstract class ObservableRunnable implements Runnable {
    final protected LifeCycleListener listener;

    public ObservableRunnable(LifeCycleListener listener) {
        this.listener = listener;
    }

    protected void notify(final RunnableEvent event){
        this.listener.onEvent(event);
    }

    @AllArgsConstructor
    @Data
    public static class RunnableEvent {
        private final RunnableState state;
        private final Thread thread;
        private final Throwable cause;

    }

    public enum RunnableState {
        TODO, RUNNING, FAILED, SUCCEED;
    }
}
