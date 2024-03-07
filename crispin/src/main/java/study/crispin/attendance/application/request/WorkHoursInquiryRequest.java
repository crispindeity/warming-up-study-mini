package study.crispin.attendance.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import study.crispin.common.LocalDateUtil;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public record WorkHoursInquiryRequest(

        @NotBlank(message = "아이디는 필수 입력값 입니다.")
        String memberId,
        @Pattern(regexp = "^2\\d{3}-(0[1-9]|1[0-2])$", message = "잘못된 형식의 날짜입니다.")
        String date
) {

    @ConstructorProperties({"member-id", "date"})
    public WorkHoursInquiryRequest(String memberId, String date) {
        this.memberId = memberId;
        this.date = date;
    }

    public static WorkHoursInquiryRequest of(String memberId, String date) {
        return new WorkHoursInquiryRequest(memberId, date);
    }

    public LocalDateTime getStartDate() {
        LocalDate parsedDate = dateParse();
        return LocalDateUtil.convertToDateOneDaysAgo(LocalDateTime.of(parsedDate, LocalTime.MIN));
    }

    public LocalDateTime getEndDate() {
        LocalDate parsedDate = dateParse();
        LocalDate startDate = LocalDate.of(parsedDate.getYear(), parsedDate.getMonth(), 1);
        return LocalDateUtil.convertToDateOneDayLater(
                LocalDateTime.of(startDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MIN)
        );
    }

    private LocalDate dateParse() {
        return LocalDate.parse(this.date.concat("-01"));
    }

    public Long getMemberId() {
        return Long.valueOf(this.memberId);
    }
}
