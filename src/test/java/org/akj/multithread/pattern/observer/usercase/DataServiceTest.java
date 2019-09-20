package org.akj.multithread.pattern.observer.usercase;

import java.util.ArrayList;
import java.util.Arrays;

class DataServiceTest {
    public static void main(String[] args) {
        new DataService().query(Arrays.asList("1", "2", "3", "4", "5"));
    }

}