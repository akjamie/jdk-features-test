package org.akj.jdk8.features;

import org.akj.jdk8.entity.MonthlyRelease;
import org.akj.jdk8.entity.Release;
import org.akj.jdk8.entity.Version;
import org.junit.jupiter.api.Test;

public class DefaultMethodTest {

	@Test
	public void test() {
		Release.logReleaseInfo(Version.builder().id("xxx-013").list("xft").build());

		new MonthlyRelease().printReleaseInfo(Version.builder().id("xxx-0135").list("xuu").build());
		
		Release release = new MonthlyRelease();
		release.printReleaseInfo(Version.builder().id("xxx-0135").list("xuu").build());

		release.test(Version.builder().id("xxx-0135").list("xuu").build());
	}

}
