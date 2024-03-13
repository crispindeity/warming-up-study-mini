package inflearn.mini.employee.dto.request;

import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public record EmployeeDailyWorkingHoursRequest(@NotNull(message = "조회하는 년도와 달은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {

    public LocalDate getEndOfMonth() {
        return yearMonth.atEndOfMonth();
    }
}
