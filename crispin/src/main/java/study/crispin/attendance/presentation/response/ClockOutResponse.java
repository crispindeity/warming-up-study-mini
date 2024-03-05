package study.crispin.attendance.presentation.response;

import java.time.LocalDateTime;

public record ClockOutResponse(Long id, String memberName, LocalDateTime clockInDateTime,
                               LocalDateTime clockOutDateTime) {
    public static ClockOutResponse of(Long id, String memberName, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
        return new ClockOutResponse(id, memberName, clockInDateTime, clockOutDateTime);
    }
}
