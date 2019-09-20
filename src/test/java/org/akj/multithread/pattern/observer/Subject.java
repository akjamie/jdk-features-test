package org.akj.multithread.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int state;

    private List<Observer> observers = new ArrayList<>();

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (this.state == state) return;

        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        observers.stream().forEach(e -> e.update(null));
    }

}
