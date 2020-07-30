package org.akj.multithread.pattern.twophasetermination;

class AppServerTest {
    public static void main(String[] args) {
        new AppServer(12890).start();
    }

}