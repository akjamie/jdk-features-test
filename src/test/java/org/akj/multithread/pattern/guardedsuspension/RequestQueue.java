package org.akj.multithread.pattern.guardedsuspension;

import java.util.LinkedList;

public class RequestQueue<T> {

    private final LinkedList<T> queue = new LinkedList<>();

    public T get() {
        synchronized (queue) {
            while (queue.size() <= 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            return queue.removeLast();
        }
    }

    public void put(T value) {
        synchronized (queue) {
            queue.addFirst(value);
            queue.notifyAll();
        }
    }
}
