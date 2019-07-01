package org.akj.jdk8.features;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.akj.jdk8.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		collect.forEach((k,v) -> {
			System.out.println(k);
			v.forEach(System.out::println);
		});
	}

}
