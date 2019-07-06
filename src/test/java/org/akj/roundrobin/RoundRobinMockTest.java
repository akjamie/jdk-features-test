package org.akj.roundrobin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoundRobinMockTest {

	private RoundRobinMock mock;

	private List<String> servers;

	@BeforeEach
	public void setup() {
		mock = new RoundRobinMock();

		servers = Arrays
				.asList(new String[] { "192.1.0.2", "192.1.0.3", "192.1.0.4", "192.1.0.5", "192.1.0.6"});
	}

	@Test
	final void testSelect() {
		List<Thread> threads = new ArrayList<Thread>();

		List<String> results = new ArrayList<String>();

		for (int i = 0; i < 20; i++) {
			threads.add(new Thread(() -> {
				String select = mock.select(servers);
				System.out.println(select);
				results.add(select);
			}));
		}

		threads.forEach(t -> t.start());
		
		results.stream().collect(Collectors.groupingBy(item -> item)).forEach((k,v) -> {
			System.out.println("Key: " + k + " Count: " + v.size());
		});
	}
	
	@Test
	public void test() {
		int cnt = 0;
		for (;;) {
			cnt ++;
			System.out.println(Math.random());
			
			if(cnt % 10 == 0) {
				break;
			}
		}
	}

}
