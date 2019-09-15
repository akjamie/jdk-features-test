package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Eager mode
 * @Author Jamie Zhang
 */
public class Singleton1 {
    private final static Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
