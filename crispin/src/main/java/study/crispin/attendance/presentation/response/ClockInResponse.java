package study.crispin.attendance.presentation.response;

import java.time.LocalDateTime;

public record ClockInResponse(Long id, String memberName, LocalDateTime clockInDateTime) {
    public static ClockInResponse of(Long id, String name, LocalDateTime clockInDateTime) {
        return new ClockInResponse(id, name, clockInDateTime);
    }
}
