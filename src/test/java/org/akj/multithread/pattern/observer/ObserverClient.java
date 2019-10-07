package org.akj.multithread.pattern.observer;

public class ObserverClient {
    public static void main(String[] args) {
        Subject subject  = new Subject();

        new HexObserver(subject);
        new OctObserver(subject);

        subject.setState(1000);
    }
}
