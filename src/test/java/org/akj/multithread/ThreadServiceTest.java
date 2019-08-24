package org.akj.multithread;

class ThreadServiceTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ThreadService service = new ThreadService();
        service.execute(() -> {
//            while (true) {
//                // FIXME
//            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown(10000);
        System.out.println("spend times(milliseconds):" + (System.currentTimeMillis() - start));
    }

}