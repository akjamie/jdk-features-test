package org.akj.jdk8.features.lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class MethodReferenceTest {

    @Test
    public void test() {
        log.info("test the static method reference");
        IntStream.generate(() -> new Random().nextInt(1000)).limit(5).forEach(System.out::println);

        log.info("test the instance method reference on a particular Object");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS Z");
        LocalDateTime localDateTime = LocalDateTime.now();
        List.of(ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York")))
                .stream().map(formatter::format).forEach(System.out::println);

        log.info("test the method reference of Constructor");
        List<String> bikeBrands = Arrays.asList("Forever", "GT", "Giant");
        long count = bikeBrands.stream().map(Bicycle::new).count();
        log.info("there are [{}] brand name bikes created", count);

    }

    class Bicycle {
        String brand;

        Bicycle(String brand) {
            this.brand = brand;
        }
    }
}
