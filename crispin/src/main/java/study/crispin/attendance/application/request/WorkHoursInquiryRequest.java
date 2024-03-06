package study.crispin.attendance.application.request;

import study.crispin.common.LocalDateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public record WorkHoursInquiryRequest(Long memberId, LocalDate date) {

    public static WorkHoursInquiryRequest of(Long memberId, LocalDate date) {
        return new WorkHoursInquiryRequest(memberId, date);
    }

    public LocalDate getStartDate() {
        return LocalDateUtil.convertToDateOneDaysAgo(LocalDateTime.of(date, LocalTime.MIN));
    }

    public LocalDate getEndDate() {
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return LocalDateUtil.convertToDateOneDayLater(
                LocalDateTime.of(startDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MIN)
        );
    }
}
