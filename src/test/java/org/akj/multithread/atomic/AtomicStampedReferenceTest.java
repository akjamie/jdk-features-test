package org.akj.multithread.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+ " stamp before sleep: " + atomicStampedReference.getStamp());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+ " stamp after sleep: " + atomicStampedReference.getStamp());
                boolean succ = atomicStampedReference.compareAndSet(100,105, atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " update succ? 100 - >  105 :" + succ);

                succ = atomicStampedReference.compareAndSet(105,110, atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " update succ? 105 - >  110 :" + succ);
                System.out.println("stamp after change: " + atomicStampedReference.getStamp());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+ " stamp before sleep: " + atomicStampedReference.getStamp());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+ " stamp after sleep: " + atomicStampedReference.getStamp());
                boolean succ = atomicStampedReference.compareAndSet(100,105, atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " update succ? 100 - >  105 :" + succ);

                succ = atomicStampedReference.compareAndSet(105,110, atomicStampedReference.getStamp(),
                        atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " update succ? 105 - >  110 :" + succ);
                System.out.println("stamp after change: " + atomicStampedReference.getStamp());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Data
    @AllArgsConstructor
    private static class Sample {
        private String key;

        private String value;
    }
}
