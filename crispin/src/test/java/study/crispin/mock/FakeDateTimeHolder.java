package study.crispin.mock;

import study.crispin.common.DateTimeHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FakeDateTimeHolder implements DateTimeHolder {

    private final LocalDate date;
    private final LocalTime time;

    public FakeDateTimeHolder(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(this.date, this.time);
    }
}
