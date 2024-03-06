package study.crispin.attendance.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import study.crispin.common.LocalDateUtil;

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

    public static WorkHoursInquiryRequest of(String memberId, String date) {
        return new WorkHoursInquiryRequest(memberId, date);
    }

    public LocalDate getStartDate() {
        LocalDate parsedDate = dateParse();
        return LocalDateUtil.convertToDateOneDaysAgo(LocalDateTime.of(parsedDate, LocalTime.MIN));
    }

    public LocalDate getEndDate() {
        LocalDate parsedDate = dateParse();
        LocalDate startDate = LocalDate.of(parsedDate.getYear(), parsedDate.getMonth(), 1);
        return LocalDateUtil.convertToDateOneDayLater(
                LocalDateTime.of(startDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MIN)
        );
    }

    private LocalDate dateParse() {
        return LocalDate.parse(this.date);
    }

    public Long getMemberId() {
        return Long.valueOf(this.memberId);
    }
}
