package study.crispin.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateUtil {

    protected LocalDateUtil() {
    }

    public static LocalDate convertToDateOneDaysAgo(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().minusDays(1L);
    }

    public static LocalDate convertToDateTwoDaysAgo(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().minusDays(2L);
    }

    public static LocalDate convertToDateOneDayLater(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().plusDays(1L);
    }
}
