package org.akj.multithread.advance;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CompletableFutureAdvanceFeatureTest {
    @SneakyThrows
    @Test
    public void test() {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                log.info("supplyAsync - get order count.");
                return "1000";
            }
        });

        CompletableFuture<BigDecimal> totalAmountFuture = task.thenApply(number -> {
            log.info("thenApply - calculate the total amount(price=12.98)");
            log.info("the total order count is {}", number);
            return new BigDecimal(number).multiply(new BigDecimal(12.98)).setScale(0, RoundingMode.HALF_UP);
        });

        CompletableFuture<BigDecimal> netAmountFuture = totalAmountFuture.thenApply(number -> {
            log.info("thenApply - calculate the net amount(tax deduction is 10%)");
            log.info("the total amount is {}", number);
            return number.subtract(number.multiply(new BigDecimal("0.1"))).setScale(2, RoundingMode.HALF_DOWN);
        });

        log.info("the final total amount is {}", netAmountFuture.get());
    }

    @SneakyThrows
    @Test
    public void test1() {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                log.info("supplyAsync - get order count.");
                return "1000";
            }
        });

        CompletableFuture<BigDecimal> totalAmountFuture = task.thenApply(number -> {
            log.info("thenApply - calculate the total amount(price=12.98)");
            log.info("the total order count is {}", number);
            return new BigDecimal(number).multiply(new BigDecimal(12.98)).setScale(0, RoundingMode.HALF_UP);
        });

        CompletableFuture<BigDecimal> netAmountFuture = totalAmountFuture.thenApply(number -> {
            log.info("thenApply - calculate the net amount(tax deduction is 10%)");
            log.info("the total amount is {}", number);
            return number.subtract(number.multiply(new BigDecimal("0.1"))).setScale(2, RoundingMode.HALF_DOWN);
        });

        CompletableFuture<Void> accept = netAmountFuture.thenAccept(netAmount -> {
            log.info("the final net amount for 2022 Q1 is {}", netAmount);
        });

        accept.get();
    }

    @SneakyThrows
    @Test
    public void test2() {
        CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync - first stage.");
            return null;
        }).thenRun(() -> {
            log.info("thenRun - last stage.");
        });
    }

    @SneakyThrows
    @Test
    public void test3() {
        CompletableFuture<String> stage1Future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync - first stage.");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "111";
        });

        CompletableFuture<String> stage2Future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync - second stage.");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "222";
        });

//        CompletableFuture<Void> acceptBoth = stage1Future.thenAcceptBoth(stage2Future, (s1, s2) -> {
//            log.info("thenAcceptBoth - s1= {}, s2={}", s1, s2);
//        });
//        acceptBoth.get();

        CompletableFuture<Integer> stage3Future = stage1Future.thenCombineAsync(stage2Future, (s1, s2) -> {
            log.info("thenComposeAsync - s1= {}, s2={}", s1, s2);
            return Integer.valueOf(s1) + Integer.valueOf(s2);
        });

        log.info(stage3Future.get() + "");
    }

    @SneakyThrows
    @Test
    public void test4() {
        CompletableFuture<String> stage1Future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync - first stage.");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "111";
        });

        CompletableFuture<Integer> stage2Future = stage1Future.thenComposeAsync(value -> CompletableFuture.supplyAsync(() -> {
            log.info("thenComposeAsync - second stage.");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Integer.valueOf(value) + Integer.valueOf("2221");
        }));

        log.info(stage2Future.get() + "");
    }

    @SneakyThrows
    @Test
    public void test5() {
        CompletableFuture<Double> array[] = new CompletableFuture[3];
        for (int i = 0; i < 3; i++) {
            array[i] = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Math.random();
            });
        }

        CompletableFuture.allOf(array).join();
        List<Double> list = Arrays.stream(array).map(CompletableFuture::join).filter(number -> number > 0.5)
                .collect(Collectors.toList());
        System.out.println(list);

        List<Double> rs = Stream.of(array)
                .map(CompletableFuture::join)
                .filter(number -> number > 0.6)
                .collect(Collectors.toList());
        System.out.println(rs);
    }


}

