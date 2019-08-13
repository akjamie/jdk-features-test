package org.akj.multithread;

import org.junit.jupiter.api.Test;

public class OutOfStackErrorTest {
    private static int counter = 0;

    @Test
    public void test() {
        try {
            add(0);
        } catch (Error e) {
            System.out.println(e.getMessage());
            System.out.println("counter:" + counter);
        }
    }

    private void add(Integer i) {
        counter++;
        add(i + 1);
    }

    @Test
    //-Xms<size>        set initial Java heap size
    //-Xmx<size>        set maximum Java heap size
    //-Xss<size>        set java thread stack size
    public void test1() {
        String str = "x-001-";
        for (int i = 1; ; i++) {
            str += i;
        }
    }

    @Test
    public void test2(){
        Thread t1 = new Thread(null, ()->{
            try {
                add(0);
            } catch (Error e) {
                System.out.println(e);
                System.out.println("counter:" + counter);
            }
        },"thread-name-001", 1);

        t1.start();
    }
}
