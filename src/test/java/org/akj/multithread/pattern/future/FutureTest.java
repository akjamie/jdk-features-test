package org.akj.multithread.pattern.future;

/**
 * Future -
 * FutureTask - wrap up the method call
 * FutureService - work as bridge between Future and FutureTask
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException {
        Future<String> future = new FutureService().submit(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "RESULT";
        });

        System.out.println("moving forward...");

        System.out.printf("the result of future is %s \n",future.get());
    }

    public static String get() throws InterruptedException{
        Thread.sleep(10_000);

        return "RESULT";
    }
}
