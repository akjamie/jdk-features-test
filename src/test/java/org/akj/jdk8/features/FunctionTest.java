package org.akj.jdk8.features;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.akj.jdk8.entity.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FunctionTest {
	@Test
	public void testPredicate() {
		Predicate<Integer> boolValue = x -> x > 5;
		Assertions.assertFalse(boolValue.test(1));// false
		Assertions.assertTrue(boolValue.test(6));// true
	}

	@Test
	public void testConsumer() {
		Version v = Version.builder().id(UUID.randomUUID().toString()).name("v1.1.9").build();
		System.out.println(v);
		
		Consumer<Version> consumer = version -> version.setName("v1.2.0");
		consumer.accept(v);
		
		System.out.println(v);
	}
	
	@Test
	public void testSupplier() {
		Supplier<Version> version = () -> Version.builder().id(UUID.randomUUID().toString()).name("v1.1.9").build();
		System.out.println(version.get());
		
	}
}
