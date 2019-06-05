package org.akj.jdk8.entity;

public interface Release {
	String seriousId = null;

	public void createRelease(Version version);

	default void printReleaseInfo(Version version) {
		System.out.println(version + "interface");
	}

	static void logReleaseInfo(Version version) {
		System.out.println(version);
	}
	
	default void test(Version version) {
		System.out.println("generic method template start");
		this.printReleaseInfo(version);
		System.out.println("generic method template end");
	}

}
