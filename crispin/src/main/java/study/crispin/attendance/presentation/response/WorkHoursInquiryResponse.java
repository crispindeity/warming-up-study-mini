package study.crispin.attendance.presentation.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkHoursInquiryResponse(LocalDate date, long workingMinutes) {

    public static WorkHoursInquiryResponse of(LocalDateTime date, long workingMinutes) {
        return new WorkHoursInquiryResponse(date.toLocalDate(), workingMinutes);
    }
}
