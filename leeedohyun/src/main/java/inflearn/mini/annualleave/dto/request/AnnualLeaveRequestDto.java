package inflearn.mini.annualleave.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record AnnualLeaveRequestDto(
        @NotNull(message = "직원 ID는 필수입니다.")
        Long employeeId,
        @NotNull(message = "연차 사용일은 필수입니다.")
        LocalDate annualLeaveDate) {
}
