package inflearn.mini.team.dto.request;

import inflearn.mini.team.domain.Team;

public record TeamRegisterRequestDto(String teamName) {

    public Team toEntity() {
        return new Team(teamName);
    }
}
