package inflearn.mini.workHub.team.dto;

import inflearn.mini.workHub.team.domain.Team;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TeamCreateRequest(
        @NotNull(message = "팀 이름은 null 일 수 없습니다.")
        @NotBlank(message = "팀 이름은 공란일 수 없습니다.")
        String name) {

    public Team toEntity() {
        return Team.builder()
                .name(name)
                .build();
    }
}
