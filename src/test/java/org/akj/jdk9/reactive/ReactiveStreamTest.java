package org.akj.jdk9.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

@Slf4j
public class ReactiveStreamTest {

    @RepeatedTest(5)
    public void test() throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher();

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;

                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                log.debug("data processed: {}", item);

                //if (item == 20) throw new IllegalStateException("simulated exception.");

                this.subscription.request(2);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                log.debug("all data processed finished..");
            }
        };

        publisher.subscribe(subscriber);

        IntSupplier supplier = () -> new Random().nextInt(100);
        IntStream.generate(supplier).limit(20).forEach(publisher::submit);

        publisher.close();

        TimeUnit.SECONDS.sleep(5);
    }

    @RepeatedTest(1)
    public void test1() throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher(ForkJoinPool.commonPool(), 3);

        Subscriber<String> subscriber = new Subscriber<String>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;

                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                log.debug("data processed: {}", item);

                if (item.startsWith("20")) throw new IllegalStateException("simulated exception.");

                this.subscription.request(2);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                log.debug("all data processed finished..");
            }
        };

        CustomProcessor processor = new CustomProcessor();
        publisher.subscribe(processor);
        processor.subscribe(subscriber);

        IntSupplier supplier = () -> new Random().nextInt(100);
        IntStream.generate(supplier).limit(50).forEach(i -> {
            log.debug("data produced {}", i);
            publisher.submit(i);
        });

        publisher.close();

        TimeUnit.SECONDS.sleep(20);
    }

    class CustomProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            log.debug("data received: {}", item);

            if (item >= 0) {
                this.submit(item + "-transferred");
            }
            this.subscription.request(5);
        }

        @Override
        public void onError(Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            this.subscription.cancel();
        }

        @Override
        public void onComplete() {
            log.debug("all data processed.");
        }
    }


}
