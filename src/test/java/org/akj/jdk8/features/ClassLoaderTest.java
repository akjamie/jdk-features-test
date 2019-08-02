package org.akj.jdk8.features;

import org.junit.jupiter.api.Test;

public class ClassLoaderTest {

    @Test
    public void test(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }


}
