package org.akj.jdk8.features;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class LocalDateTest {
    @Test
    public void test() {

        log.info("-----------LocalDate/LocalDateTime---[No TimeZone]--------");
        LocalDate date = LocalDate.parse("2021-11-29", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(Month.NOVEMBER, date.getMonth());

        assertEquals(2022, date.plusYears(1).getYear());
        assertEquals(25, date.minusDays(4).getDayOfMonth());

        LocalDateTime localDateTime = date.atTime(LocalTime.now());
        log.debug(localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        log.info("------------ZonedDateTime------------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        ZoneId chicago = ZoneId.of("America/Chicago");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, chicago);
        log.debug("base date time: [{}]", formatter.format(zonedDateTime));
        log.debug("timezone: [{}], date time: [{}]", chicago, formatter.format(zonedDateTime));

        ZoneId newYork = ZoneId.of("America/New_York");
        ZonedDateTime newYorkTime = zonedDateTime.withZoneSameInstant(newYork);
        log.debug("timezone: [{}], date time: [{}]", newYork, formatter.format(newYorkTime));

        ZoneId systemDefault = ZoneId.systemDefault();
        ZonedDateTime defaultTime = zonedDateTime.withZoneSameInstant(systemDefault);
        log.debug("timezone: [{}], date time: [{}]", systemDefault, formatter.format(defaultTime));

        ZonedDateTime defaultTimeLocal = zonedDateTime.withZoneSameLocal(systemDefault);
        log.debug("timezone: [{}], date time: [{}]", systemDefault, formatter.format(defaultTimeLocal));

        log.info("------------Instant------------------");
        Instant ins = Instant.now();
        log.debug("Instant in UTC: {}", ins);
        ZonedDateTime ofInstant = ZonedDateTime.ofInstant(ins, ZoneId.systemDefault());
        log.debug("timezone: [{}], date time: [{}]", systemDefault, formatter.format(ofInstant));
        log.debug("timezone: [{}], date time: [{}]", systemDefault, ofInstant);

        ZoneOffset zoneOffSet= ofInstant.getOffset();
        OffsetDateTime offsetDateTime = OffsetDateTime.now(zoneOffSet);
        log.debug("timezone: [{}], date time: [{}]", systemDefault, offsetDateTime);
    }
}
