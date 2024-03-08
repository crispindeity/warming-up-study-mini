package study.crispin.mock;

import study.crispin.common.DateTimeHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FakeDateTimeHolder implements DateTimeHolder {

    private final LocalDate date;
    private final LocalTime time;

    public FakeDateTimeHolder() {
        this.date = LocalDate.of(2024, 2, 29);
        this.time = LocalTime.of(9, 0, 0);
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(this.date, this.time);
    }
}
