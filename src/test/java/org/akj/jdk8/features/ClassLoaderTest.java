package org.akj.jdk8.features;

import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.sql.SQLException;

public class ClassLoaderTest {

    @Test
    public void test() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
    }

    @Test
    public void test1() {
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    public static void main(String[] args) throws SQLException {
//        System.out.println(System.getProperty("sun.boot.class.path"));
//        System.out.println(System.getProperty("java.ext.dirs"));
//
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Driver.class.getClassLoader());
        System.out.println(com.mysql.cj.jdbc.Driver.class.getClassLoader());

        Driver driver =  new com.mysql.cj.jdbc.Driver();
    }

}
