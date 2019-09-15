package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode (not real singleton, it may have more than 1 instance under multiple thread
 * circumstances)
 *
 * @Author Jamie Zhang
 */
public class Singleton2 {
    private static Singleton2 instance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (null == instance)
            instance = new Singleton2();

        return instance;
    }
}
