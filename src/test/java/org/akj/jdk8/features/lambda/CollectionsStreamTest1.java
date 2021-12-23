package org.akj.jdk8.features.lambda;

import org.akj.jdk8.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CollectionsStreamTest1 {

    private List<Person> persons;

    @BeforeEach
    public void setup() {
        persons = new ArrayList<Person>();

        persons.add(new Person("Robert", "Male", "Single"));
        persons.add(new Person("John", "Female", "Married"));
        persons.add(new Person("Mike", "Male", "Married"));
        persons.add(new Person("Richard", "Male", "Single"));
    }

    @Test
    void test() {
        Map<String, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(Person::getMaritalStatus));
        collect.forEach((k, v) -> {
            System.out.println(k);
            v.forEach(System.out::println);
        });
    }

    @Test
    void test1() {
        Map<String, List<String>> collect = persons.stream().collect(Collectors.groupingBy(Person::getMaritalStatus,
                Collectors.mapping(Person::getName, Collectors.toList())));
        collect.forEach((k, v) -> {
            System.out.println(k);
            v.forEach(System.out::println);
        });
    }

}
