package inflearn.mini.annualleave.dto.request;

import jakarta.validation.constraints.NotNull;

public record RemainingAnnualLeaveRequestDto(@NotNull Long employeeId) {
}
