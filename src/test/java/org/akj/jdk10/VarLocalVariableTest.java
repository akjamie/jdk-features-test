package org.akj.jdk10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class VarLocalVariableTest {

    @Test
    public void test() {
        var s = "this is testing string";
        System.out.println(s);

        var list = new ArrayList<>();
//        List<Integer> list = new ArrayList<>();
        list.add(Integer.MIN_VALUE);
        list.add("test message");
        list.add(1.01);
        list.stream().forEach(System.out::println);
    }
}
