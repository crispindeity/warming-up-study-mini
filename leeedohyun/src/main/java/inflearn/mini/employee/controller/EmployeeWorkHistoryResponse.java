package inflearn.mini.employee.controller;

import java.util.List;

import inflearn.mini.worktimehistory.service.DateWorkMinutes;

public record EmployeeWorkHistoryResponse(List<DateWorkMinutes> detail, long sum) {
}
