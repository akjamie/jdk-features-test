package org.akj.multithread.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class DataCollectionService {
    private static List<Thread> threads = new ArrayList<>();

    private static final int MAX_WORKER_IN_PARALLEL = 5;

    private static LinkedList CONTROLS = new LinkedList();

    private static String[] names = new String[]{"Worker-01", "Worker-02", "Worker-03", "Worker-04", "Worker-05",
            "Worker-06", "Worker-07"};

    private static Thread createWorker(String name) {
        return new Thread(() -> {
            Optional.of("The worker [" + name + "] has been created...").ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() > MAX_WORKER_IN_PARALLEL) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLS.add(new Object());
            }

            Optional.of("The worker [" + name + "] is running...").ifPresent(System.out::println);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (CONTROLS) {
                Optional.of("The worker [" + name + "] is finished...").ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);
    }

    public static void main(String[] args) {
        Stream.of(names).map(DataCollectionService::createWorker).forEach(t -> {
            t.start();
            threads.add(t);
        });

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("all workers have finished data capture....").ifPresent(System.out::println);
    }
}
