package org.akj.multithread.pattern.observer.usercase;

import lombok.Data;

@Data
public class DataServiceRunnableEvent extends ObservableRunnable.RunnableEvent {
    private String taskId;

    public DataServiceRunnableEvent(String id, ObservableRunnable.RunnableState state, Thread thread, Throwable cause) {
        super(state, thread, cause);

        this.taskId = id;
    }
}
