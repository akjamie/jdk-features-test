package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode
 *
 * @Author Jamie Zhang
 */
public class Singleton5 {

    private static volatile Singleton5 instance;

    private Singleton5() {
    }

    public static Singleton5 getInstance() {
        if (null == instance) {
            synchronized (Singleton5.class) {
                if (null == instance)
                    instance = new Singleton5();
            }
        }

        return instance;
    }
}
