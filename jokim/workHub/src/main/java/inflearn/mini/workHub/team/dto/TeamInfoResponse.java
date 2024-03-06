package inflearn.mini.workHub.team.dto;

import inflearn.mini.workHub.team.domain.Team;
import lombok.Builder;

@Builder
public record TeamInfoResponse(String name, String managerName, int employeeCount) {

    public static TeamInfoResponse from(final Team team) {
        return TeamInfoResponse.builder()
                .name(team.getName())
                .managerName(team.getManagerName())
                .employeeCount(team.getEmployeeCount())
                .build();
    }
}
