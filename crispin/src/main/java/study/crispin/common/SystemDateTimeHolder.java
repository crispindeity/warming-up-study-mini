package study.crispin.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemDateTimeHolder implements DateTimeHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
