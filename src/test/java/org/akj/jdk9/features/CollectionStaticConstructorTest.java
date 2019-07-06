package org.akj.jdk9.features;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class CollectionStaticConstructorTest {
	
	@Test
	public void test() {
		List<String> cities = List.of("Beijing","Guangzhou","Shanghai","ChongQing","Xian");
		cities.parallelStream().takeWhile(item -> item.contains("ing")).forEach(System.out::println);
		
		System.out.println("-----------------");
		
		cities.parallelStream().dropWhile(item -> item.contains("ing")).forEach(System.out::println);
	}

	@Test
	public void test1() {
		Set<String> cities = Set.of("Beijing","Guangzhou","Shanghai","ChongQing","Xian");
		cities.parallelStream().takeWhile(item -> item.contains("ing")).forEach(System.out::println);
		
		System.out.println("-----------------");
		
		cities.parallelStream().dropWhile(item -> item.contains("ing")).forEach(System.out::println);
	}
}
