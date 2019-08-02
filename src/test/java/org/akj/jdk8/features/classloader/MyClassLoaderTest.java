package org.akj.jdk8.features.classloader;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

class MyClassLoaderTest {

    @Test
    public  void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setClassPath("C:\\Users\\Jamie Y L Zhang\\Desktop\\tmp");

        Class<?> testClass = null;
        testClass = classLoader.loadClass("org.akj.jdk8.entity.Version1");

        Object object = null;
        Constructor<?> constructor = testClass.getConstructor(String.class, String.class, String.class, Date.class, String.class, List.class);
        object = constructor.newInstance("test-1","test-1","test-1",new Date(),"test-1",null);
        System.out.println(object.getClass().getClassLoader());
        System.out.println(object.getClass().getClassLoader().getParent());
        System.out.println(object.getClass().getClassLoader().getParent().getParent());
        System.out.println(object.getClass().getClassLoader().getParent().getParent().getParent());

    }


}