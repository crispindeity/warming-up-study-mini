package inflearn.mini.annualleave.dto.request;

import java.time.LocalDate;

public record AnnualLeaveRequestDto(Long employeeId, LocalDate annualLeaveDate) {
}
