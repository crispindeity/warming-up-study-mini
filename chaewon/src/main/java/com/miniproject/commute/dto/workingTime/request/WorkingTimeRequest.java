package com.miniproject.commute.dto.workingTime.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WorkingTimeRequest(
        @NotNull(message = "직원 id를 필수로 입력해야 합니다.")
        long memberId,
        @NotNull(message = "연도와 날짜를 입력해야 합니다.(예시: 2024-02)")
        @Pattern(regexp = "^\\d{4}-\\d{2}$")
        String yearAndMonth) {
}
