package org.akj;

import javassist.CannotCompileException;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GCTest {
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException, CannotCompileException {
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100), new ThreadPoolExecutor.DiscardPolicy());
        executor.prestartAllCoreThreads();
        Random random = new Random(100000);

        StringBuilder builder = new StringBuilder();
        while (true) {
            executor.submit(() -> {
                System.out.println("new thread created, thread: " + Thread.currentThread().getName());
//                synchronizedList.add(random.nextInt());
//                local.set(UUID.randomUUID().toString());
                builder.append(UUID.randomUUID().toString());
            });
        }

//        ClassPool classPool = ClassPool.getDefault();
//        for (int i = 0; ; i++) {
//            TimeUnit.MILLISECONDS.sleep(50);
//            Class c = classPool.makeClass("metaspace.test.Class_" + i).toClass();
//        }

        //-Xss=256K
        //new GCTest().test();

    }

    private int depth = 0;

    public void test() {
        this.depth++;
        System.out.println("depth: " + this.depth);
        test();
    }

}
