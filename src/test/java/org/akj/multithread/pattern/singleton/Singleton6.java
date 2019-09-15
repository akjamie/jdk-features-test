package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode
 *
 * @Author Jamie Zhang
 */
public class Singleton6 {

    private Singleton6() {
    }

    public static Singleton6 getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder{
        private final static Singleton6 instance = new Singleton6();
    }
}
