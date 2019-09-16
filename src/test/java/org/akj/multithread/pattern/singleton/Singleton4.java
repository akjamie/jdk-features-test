package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode(may get null value for some field/attribute, object allocated in heap but not
 * completed initialization)
 *
 * @Author Jamie Zhang
 */
public class Singleton4 {
    private static Singleton4 instance;

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        if (null == instance) {
            synchronized (Singleton4.class) {
                if (null == instance)
                    instance = new Singleton4();
            }
        }

        return instance;
    }
}
