package inflearn.mini.employee.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

public record EmployeeWorkHistoryRequest(@DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {

    public int year() {
        return yearMonth.getYear();
    }

    public Month month() {
        return yearMonth.getMonth();
    }

    public LocalDate getEndOfMonth() {
        return yearMonth.atEndOfMonth();
    }
}
