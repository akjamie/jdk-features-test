package org.akj.roundrobin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinMock {
	private final static AtomicInteger nextCounter = new AtomicInteger(0);

	public String select(List<String> servers) {
		if (servers == null || servers.size() == 0)
			throw new RuntimeException("system error...");
		else {
			int nextServerIndex = incrementAndGetModulo(servers.size());

			return servers.get(nextServerIndex);
		}
	}

	private int incrementAndGetModulo(int modulo) {
		for (;;) {
			int current = nextCounter.get();
			int next = (current + 1) % modulo;
			if (nextCounter.compareAndSet(current, next))
				return next;
		}
	}
}
