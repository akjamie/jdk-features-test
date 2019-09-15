package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode
 *
 * @Author Jamie Zhang
 */
public class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {
    }

    public synchronized static Singleton3 getInstance() {
        if (null == instance)
            instance = new Singleton3();

        return instance;
    }
}
