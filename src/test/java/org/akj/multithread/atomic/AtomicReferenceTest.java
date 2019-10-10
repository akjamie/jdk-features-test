package org.akj.multithread.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static void main(String[] args) {
        Sample sample = new Sample("Key", "Value");
        AtomicReference<Sample> atomicReference = new AtomicReference<Sample>(sample);

        System.out.println(atomicReference.get());

        boolean result = atomicReference.compareAndSet(sample, new Sample("KEY-1","V-1"));
        System.out.println(result);

        result = atomicReference.compareAndSet(new Sample("AK-47", "WEAPONS"), new Sample("KEY-1","V-1"));
        System.out.println(result);
    }

    @Data
    @AllArgsConstructor
    private static class Sample {
        private String key;

        private String value;
    }
}
