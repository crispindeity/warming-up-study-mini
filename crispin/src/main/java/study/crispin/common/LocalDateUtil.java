package study.crispin.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateUtil {

    protected LocalDateUtil() {
    }

    public static LocalDateTime convertToDateOneDaysAgo(LocalDateTime localDateTime) {
        return localDateTime.minusDays(1L).truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime convertToDateTwoDaysAgo(LocalDateTime localDateTime) {
        return localDateTime.minusDays(2L).truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime convertToDateOneDayLater(LocalDateTime localDateTime) {
        return localDateTime.plusDays(1L).truncatedTo(ChronoUnit.DAYS);
    }
}
