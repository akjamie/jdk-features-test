package org.akj.jdk8.features;

import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Optional;

public class ClassLoaderTest {

    @Test
    public void test() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("print the class loader");
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());

        System.out.println("print the class loader name:");
        System.out.println(loader.getName());
        System.out.println(loader.getParent().getName());
        System.out.println(!Optional.ofNullable(loader.getParent().getParent()).isPresent() ? "bootstrap" : loader.getParent().getParent().getName());
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

        Driver driver = new com.mysql.cj.jdbc.Driver();
    }

}
