package org.akj.multithread.advance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportTest {
    public static void main(String[] args) {
        AtomicInteger inventory = new AtomicInteger();
        Integer maxCapacity = 10;
        List<Thread> consumers = new ArrayList<>();
        List<Thread> providers = new ArrayList<>();
        ReentrantLock plock = new ReentrantLock();
        ReentrantLock clock = new ReentrantLock();

        Producer p1 = new Producer(maxCapacity, inventory, consumers, plock);
        p1.start();
//        Producer p2 = new Producer(maxCapacity, inventory, consumers, plock);
//        p2.start();
//        Producer p3 = new Producer(maxCapacity, inventory, consumers, plock);
//        p3.start();
//        Producer p4 = new Producer(maxCapacity, inventory, consumers, plock);
//        p4.start();
        providers.add(p1);
//        providers.add(p2);
//        providers.add(p3);
//        providers.add(p4);

        Consumer c1 = new Consumer(maxCapacity, inventory, providers, clock);
        c1.start();
        Consumer c2 = new Consumer(maxCapacity, inventory, providers, clock);
        c2.start();
        consumers.add(c1);
        consumers.add(c2);
    }

    static class Producer extends Thread {
        private final ReentrantLock plock;
        int maxCapacity = 0;
        AtomicInteger inventory;
        List<Thread> consumers;

        Producer(int size, AtomicInteger inventory, List<Thread> consumers, ReentrantLock plock) {
            this.maxCapacity = size;
            this.inventory = inventory;
            this.consumers = consumers;
            this.plock = plock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    plock.lock();
                    Thread.sleep(200);

                    String name = Thread.currentThread().getName();
                    if (inventory.get() >= maxCapacity) {
                        System.out.println("[" + name + "]Inventory is full, stop to produce, capacity: " + inventory.get());
                        unparkAll();
                        LockSupport.park();
                    } else {

                        int capacity = inventory.addAndGet(1);
                        System.out.println(name + " - produced one, capacity: " + capacity);

                        //notify consumer in case of blocking as no capacity
                        unparkAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    plock.unlock();
                }
            }
        }

        private void unparkAll() {
            consumers.stream().forEach(LockSupport::unpark);
        }
    }

    static class Consumer extends Thread {
        private final ReentrantLock clock;
        List<Thread> providers;
        int maxCapacity = 0;
        AtomicInteger inventory;

        Consumer(int size, AtomicInteger inventory, List<Thread> providers, ReentrantLock clock) {
            this.maxCapacity = size;
            this.inventory = inventory;
            this.providers = providers;
            this.clock = clock;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    clock.lock();
                    Thread.sleep(200);
                    String name = Thread.currentThread().getName();
                    if (inventory.get() <= 0) {
                        System.out.println("[" + name + "]running out of stock, stop to consume, capacity: 0");
                        unparkAll();
                        LockSupport.park();
                    } else {
                        int capacity = inventory.decrementAndGet();
                        System.out.println(name + " - consumed one, capacity: " + capacity);

                        unparkAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    clock.unlock();
                }
            }
        }

        private void unparkAll() {
            providers.stream().forEach(LockSupport::unpark);
        }
    }

}
