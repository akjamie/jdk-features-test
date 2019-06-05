package org.akj.jdk8.entity;

public class MonthlyRelease implements Release {
	
	public static String frequency;

	@Override
	public void createRelease(Version version) {
		// TODO Auto-generated method stub

	}

	static void logReleaseInfo(Version version) {
		System.out.println(version);
	}
	
	public void printReleaseInfo(Version version) {
		System.out.println(version + "subclass");
	}
}
