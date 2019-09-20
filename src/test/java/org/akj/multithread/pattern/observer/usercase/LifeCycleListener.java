package org.akj.multithread.pattern.observer.usercase;

public interface LifeCycleListener {
    void onEvent(ObservableRunnable.RunnableEvent event);
}
