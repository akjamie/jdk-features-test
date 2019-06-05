package org.akj.jdk8.features;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

class DateTimeTest {

	@Test
	void test() {
		// Date to LocalDate
		Date dt = new Date();
		Instant instant = dt.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println(localDate);

		// LocalDateTime to date
		System.out.println(LocalDateTime.now());
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.of("Z")));
		System.out.println(localDateTime);
		dt = (Date) Date.from(localDateTime.toInstant(ZoneOffset.of("-8")));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
		System.out.println(localDateTime.format(formatter));

		// new LocalDate with specifying time zone
		System.out.println(LocalDate.now(ZoneId.of("Asia/Tokyo")));

		// date string parse to local date
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse("2016-11-23", formatter);
		System.out.println(ld + ", leap year?: " + ld.isLeapYear());

		System.out.println(ld.minusMonths(1));
		System.out.println(ld.plusDays(37));

		// Calendar to Instant
		Instant time = Calendar.getInstance().toInstant();
		System.out.println(time);

		// TimeZone to ZoneId
		ZoneId defaultZone = TimeZone.getDefault().toZoneId();
		System.out.println(defaultZone);

		// Date API to Legacy classes
		dt = Date.from(Instant.now());
		System.out.println(dt);

		System.out.print(localDate.format(formatter));
	}

}
