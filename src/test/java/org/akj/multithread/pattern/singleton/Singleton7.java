package org.akj.multithread.pattern.singleton;

/**
 * design pattern - singleton - Lazy mode
 *
 * @Author Jamie Zhang
 */
public class Singleton7 {

    private Singleton7() {
    }

    public static Singleton7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private final Singleton7 instance;

        Singleton() {
            instance = new Singleton7();
        }

        public Singleton7 getInstance(){
            return instance;
        }
    }
}
