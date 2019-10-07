package org.akj.multithread.pattern.guardedsuspension;

public class SuspensionTest {
    public static void main(String[] args) throws InterruptedException {
        RequestQueue<BaseRequest> queue = new RequestQueue<>();

        new ClientThread(queue).start();

        ServerThead serverThead = new ServerThead(queue);
        serverThead.start();
        //serverThead.join();

        Thread.sleep(5000);

        //new ClientThread(queue).start();

        serverThead.close();
    }
}
