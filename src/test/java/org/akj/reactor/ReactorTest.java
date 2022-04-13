package org.akj.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class ReactorTest {

    @Test
    public void test() {
        Flux.just(1, 2, 3, 4);
        Mono.just(1);

        List<Integer> list = List.of(1, 2, 3, 4);
        Flux.fromStream(list.stream());

        Flux.fromArray(list.toArray());

        Flux.from(publisher -> new SubmissionPublisher().submit(1));

        Flux.fromIterable(list);

    }
}
