package inflearn.mini.team.dto.request;

import jakarta.validation.constraints.NotNull;

import inflearn.mini.team.domain.Team;

public record TeamRegisterRequestDto(@NotNull(message = "팀 이름은 필수입니다.") String teamName) {

    public Team toEntity() {
        return new Team(teamName);
    }
}
