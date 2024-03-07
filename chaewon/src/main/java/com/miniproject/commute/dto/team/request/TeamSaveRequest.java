package com.miniproject.commute.dto.team.request;

import jakarta.validation.constraints.NotNull;

public record TeamSaveRequest(
        @NotNull(message = "팀 이름을 필수로 입력해야 합니다.")
        String name,
        @NotNull(message = "팀의 휴가 신청 기한을 필수로 설정해야 합니다.")
        Integer dayOffPeriod
) {
}
