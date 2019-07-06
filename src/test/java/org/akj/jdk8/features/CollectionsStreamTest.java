package org.akj.jdk8.features;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionsStreamTest {

	private List<Integer> list = null;

	@BeforeEach
	public void setup() {
		list = new ArrayList<Integer>();

		for (int i = 0; i < 5; i++) {
			list.add((int) (Math.random() * 100));
		}
	}

	@Test
	public void test1() {
		list.forEach((val) -> System.out.println(val));

		System.out.println(list.stream().allMatch(n -> n > 50));

		System.out.println(list.stream().anyMatch(n -> n % 5 == 0));

		System.out.println(list.stream().collect(Collectors.summarizingInt(n -> n)).getAverage());

		System.out.println(list.stream().collect(Collectors.maxBy(Comparator.naturalOrder())));

		System.out.println(list.stream().filter(n -> n > 50).count());

		list.stream().flatMapToInt(num -> IntStream.of(num).sorted()).forEach(System.out::println);

	}

	@SuppressWarnings("static-access")
	@Test
	public void test2() {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		for (int i = 0; i < 100000; i++) {
			list.add(new BigDecimal(2).valueOf(Math.random() * 100000).setScale(2, RoundingMode.HALF_UP));
			// list.add(new BigDecimal(2).valueOf(1).setScale(2, RoundingMode.HALF_UP));
		}

		list.subList(0, 9).forEach(e -> System.out.println(e));

		// sum using for each
		System.out.println("Traditional Summ:");
		long start = System.currentTimeMillis();
		BigDecimal sum = new BigDecimal(0);

		for (BigDecimal bigDecimal : list) {
			sum = sum.add(bigDecimal);
		}

		System.out.println("Salary cost:" + sum.doubleValue() + ", time consumption:"
				+ (System.currentTimeMillis() - start) + " milliseconds");

		// sum using stream - I
		System.out.println("Stream Summ:");
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);

		DoubleSummaryStatistics dss = list.stream().collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
		System.out.println("Salary cost:" + dss.getSum() + ", time consumption:" + (System.currentTimeMillis() - start)
				+ " milliseconds");

		// sum using stream - II
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);

		Double summ = list.stream().mapToDouble(v -> v.doubleValue()).reduce(0, (x, y) -> x + y);
		System.out.println(
				"Salary cost:" + summ + ", time consumption:" + (System.currentTimeMillis() - start) + " milliseconds");

		// sum using stream - III
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);

		summ = list.stream().mapToDouble(BigDecimal::doubleValue).sum();
		System.out.println(
				"Salary cost:" + summ + ", time consumption:" + (System.currentTimeMillis() - start) + " milliseconds");

		// sum using parallel stream - I
		System.out.println("ParallelStream Summ:");
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);

		dss = list.parallelStream().collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
		System.out.println("Salary cost:" + dss.getSum() + ", time consumption:" + (System.currentTimeMillis() - start)
				+ " milliseconds");

		// sum using parallel stream - II
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);
		double sumVal = list.parallelStream().mapToDouble(v -> v.doubleValue()).reduce(0, (x, y) -> x + y);
		System.out.println("Salary cost:" + sumVal + ", time consumption:" + (System.currentTimeMillis() - start)
				+ " milliseconds");

		// sum using parallel stream - III
		start = System.currentTimeMillis();
		sum = new BigDecimal(0);
		sumVal = list.parallelStream().mapToDouble(BigDecimal::doubleValue).sum();
		System.out.println("Salary cost:" + sumVal + ", time consumption:" + (System.currentTimeMillis() - start)
				+ " milliseconds");
	}

	@Test
	public void test3() {
		list.stream().mapToInt(Integer::intValue).forEach(System.out::println);
		System.out.println(list.stream());
		System.out.println(list.stream().mapToInt(Integer::intValue));
		System.out.println(list.stream().map(Integer::intValue));
	}

}
