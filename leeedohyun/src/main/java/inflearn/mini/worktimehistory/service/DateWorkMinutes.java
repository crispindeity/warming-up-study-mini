package inflearn.mini.worktimehistory.service;

import java.time.LocalDate;

public record DateWorkMinutes(LocalDate date, long workMinutes) {

    public static DateWorkMinutes of(final LocalDate date, final long workMinutes) {
        return new DateWorkMinutes(date, workMinutes);
    }

}
