package com.miniproject.commute.dto.workingTime.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DayOffApplicationRequest(
        @NotNull(message = "직원의 id는 필수로 입력해야 합니다.")
        Long memberId,
        @NotNull(message = "휴가 신청일을 필수로 입력해야 합니다.")
        LocalDate dateOfDayOff
) {
}
