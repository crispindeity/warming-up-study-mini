package inflearn.mini.workHub.team.dto;

import inflearn.mini.workHub.team.domain.Team;
import jakarta.validation.constraints.NotBlank;


public record TeamCreateRequest(
        @NotBlank(message = "팀 이름은 공란일 수 없습니다.")
        String name) {

    public Team toEntity() {
        return new Team(name);
    }
}
