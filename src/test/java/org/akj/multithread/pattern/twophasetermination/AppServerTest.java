package org.akj.multithread.pattern.twophasetermination;

import static org.junit.jupiter.api.Assertions.*;

class AppServerTest {
    public static void main(String[] args) {
        new AppServer(12890).start();
    }

}