package com.miniproject.commute.dto.member.request;

import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.Team;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberSaveRequest(
        @NotNull(message = "직원 이름을 필수로 입력해야 합니다.")
        String name,
        @NotNull(message = "매니저 여부를 입력해야 합니다. (1: 매니저, 0: 일반 직원)")
        boolean isManager,
        @NotNull(message = "입사일을 필수로 입력해야 합니다.")
        LocalDate joinDate,
        @NotNull(message = "생일을 필수로 입력해야 합니다.")
        LocalDate birthday,
        Long teamId) {

    public Member toEntity(Team team){
        return Member.builder()
                .name(this.name)
                .joinDate(this.joinDate)
                .birthday(this.birthday)
                .team(team)
                .isManager(isManager)
                .build();
    }
}
