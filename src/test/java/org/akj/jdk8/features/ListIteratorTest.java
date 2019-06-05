package org.akj.jdk8.features;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.akj.jdk8.entity.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListIteratorTest {

	private List<Version> versions = new ArrayList<Version>(5);

	@BeforeEach
	public void setup() {

		for (int i = 1; i < 5; i++) {
			Version version = Version.builder().id("xxx-01" + i).createDate(new Date()).number("v1.3.1" + i)
					.releaseNotes("test release").list("XHR-123" + i).list("XHRX-001" + i).build();

			versions.add(version);
		}
	}

	@Test
	public void testIterator() {
		Iterator<Version> iterator = versions.iterator();

		while (iterator.hasNext()) {
			Version obj = iterator.next();
			System.out.println(obj);
		}

		versions.forEach(new Consumer<Version>() {

			@Override
			public void accept(Version t) {
				t.setReleaseNotes(t.getReleaseNotes() + "/r/n but please do pay more attention on the flow");
				System.out.println("changes:" + t);
			}

		});
	}

	@Test
	public void testListIterator() {
		ListIterator<Version> subList = versions.listIterator(2);
		Version version = Version.builder().id("xxx-01").createDate(new Date()).number("v1.3.1")
				.releaseNotes("test release").list("XHR-123").list("XHRX-001").build();

		final AtomicInteger cnt = size(subList);
		Assertions.assertEquals(2, cnt.get());

		// subList.add(version);

	}

	private AtomicInteger size(ListIterator<Version> subList) {
		final AtomicInteger cnt = new AtomicInteger(0);
		subList.forEachRemaining(new Consumer<Version>() {

			@Override
			public void accept(Version t) {
				cnt.addAndGet(1);
			}

		});
		return cnt;
	}

}
