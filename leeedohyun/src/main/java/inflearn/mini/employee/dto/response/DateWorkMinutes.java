package inflearn.mini.employee.dto.response;

import java.time.LocalDate;

public record DateWorkMinutes(LocalDate date, long workMinutes, boolean usingDayOff) {

    public static DateWorkMinutes of(final LocalDate date, final long workMinutes, final boolean usingDayOff) {
        return new DateWorkMinutes(date, workMinutes, usingDayOff);
    }
}
