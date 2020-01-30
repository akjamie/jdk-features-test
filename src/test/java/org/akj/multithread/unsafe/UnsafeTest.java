package org.akj.multithread.unsafe;

import sun.misc.Unsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnsafeTest {
    /**
     * Time Wasted:
     * StupiedCounter
     *   Current value of Counter:888759222
     *   Total time used in milliseconds:364
     * SynchronizedCounter
     *   Current value of Counter:1000000000
     *   Total time used in milliseconds:23352
     * LockedCounter
     *   Current value of Counter:1000000000
     *   Total time used in milliseconds:48750
     * AtomicCounter
     *   Current value of Counter:1000000000
     *   Total time used in milliseconds:19833
     * CASCounter
     *   Current value of Counter:1000000000
     *   Total time used in milliseconds:130130
     */

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        // security exception
        //Unsafe unsafe = Unsafe.getUnsafe();

        Unsafe unsafe = UnsafeUtils.getUnsafe();
        System.out.println(unsafe);
//        Counter counter = new StupiedCounter();
//        Counter counter = new SynchronizedCounter();
//        Counter counter = new LockedCounter();
//        Counter counter = new AtomicCounter();
        Counter counter = new CASCounter();

        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(1000);
        for(int i = 0; i< 100000; i++){
            service.submit(new CounterRunable(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);
        System.out.println("Current value of Counter:" + counter.getCounter());
        System.out.println("Total time used in milliseconds:" + (System.currentTimeMillis() - start));

    }

}
