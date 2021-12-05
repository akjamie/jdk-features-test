package org.akj.jdk9.features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class CollectionStaticConstructorTest {

	@Test
	public void test() {
		List<String> cities = List.of("Beijing","Guangzhou","Shanghai","ChongQing","Xian");

		// the list object - cities created via static constructor and output as immutable list,
		// could not do dynamic add operation on it
//		cities.add("NewYork");
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

	@Test
	public void test2() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			List<String> cities = List.of("Beijing", "Guangzhou", "Shanghai", "ChongQing", "Xian");

			// the list object - cities created via static constructor and output as immutable list,
			// could not do dynamic add operation on it
			cities.add("NewYork");
		});
	}

	@Test
	public void test3(){
		String testItem = null;
		Stream<String> stream = Stream.of("apple", "banana", "orange");
		Stream<String> stream2 = Stream.concat(stream, Stream.ofNullable(testItem));
		stream2.forEach(System.out::println);
	}
}
