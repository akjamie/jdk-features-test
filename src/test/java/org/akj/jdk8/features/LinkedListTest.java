package org.akj.jdk8.features;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListTest {

    @Test
    public void test() {
        LinkedList<String> list = new LinkedList<String>();

        list.add("001");
        list.add("002");
        list.add("003");

        list.addAll(Arrays.asList("4","5","6","7","8"));

        // list.remove(3);
        list.remove("6");

        System.out.println(list.getFirst());
        System.out.println(list.getLast());
    }
}
