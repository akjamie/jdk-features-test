package org.akj.jdk8.features;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashSetTest {
    @Test
    public void test() {
        Random rand = new Random(47);
        Set<Integer> intSet = new HashSet<Integer>();
        for (int i = 0; i < 10000; i++)
            intSet.add(rand.nextInt(11));
        intSet.add(18);
        System.out.println(intSet);
    }

    @Test
    public void test1() {
        Set<Integer> hs = new HashSet<Integer>();
        hs.add(1);
        hs.add(18);
        hs.add(2);
        hs.add(3);
        hs.add(4);
        //增强for遍历
        for (Integer s : hs) {
            System.out.print(s + " ");
        }
    }

    @Test
    public void test2() {
        Random rand = new Random(47);
        System.out.println(rand.nextInt(11));
        System.out.println(rand.nextInt(11));
    }

    @Test
    public  void testHash(){
        Integer key = 17;
        Integer [] tab = new Integer[16];
        int h = key.hashCode();
        System.out.println(key + " -> hashcode: " + h);
        int hash = h ^ (h >>> 16);
        System.out.println(key + " -> final hash[h = key.hashCode()) ^ (h >>> 16)]: " + hash);
        System.out.println("hash value for HashMap: " + String.valueOf((tab.length-1)&hash));
    }
}
