package me.sungbin.domain.team.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.sungbin.domain.team.entity.Team;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.model.request
 * @fileName : CreateTeamRequestDto
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public record RegistrationTeamRequestDto(
        @NotBlank(message = "이름은 공란일 수 없습니다.")
        @NotNull(message = "이름은 null일 수 없습니다.")
        String name,

        @NotNull(message = "연차 팀 정책은 null일 수 없습니다.")
        int annualLeavePolicy
) {

    public Team toEntity() {
        return Team.builder()
                .name(name)
                .dayBeforeLeaveRequired(annualLeavePolicy)
                .build();
    }
}
