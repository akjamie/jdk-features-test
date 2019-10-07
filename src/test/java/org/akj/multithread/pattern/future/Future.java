package org.akj.multithread.pattern.future;

public interface Future<T> {
    T get() throws InterruptedException;
}
