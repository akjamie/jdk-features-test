package org.akj.multithread.pattern.observer;

public abstract class Observer {
    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;

        this.subject.attach(this);
    }

    public abstract void update(Object args);
}
