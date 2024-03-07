package inflearn.mini.team.dto.response;

import inflearn.mini.team.domain.Team;
import lombok.Builder;

@Builder
public record TeamResponseDto(String name, String managerName, int memberCount) {

    public static TeamResponseDto from(final Team team) {
        return TeamResponseDto.builder()
                .name(team.getName())
                .managerName(team.getManagerName())
                .memberCount(team.countEmployees())
                .build();
    }
}
