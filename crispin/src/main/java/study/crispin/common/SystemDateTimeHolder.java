package study.crispin.common;

import study.crispin.common.DateTimeHolder;

import java.time.LocalDateTime;

public class SystemDateTimeHolder implements DateTimeHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
