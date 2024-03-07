package inflearn.mini.workHub.team.dto;

import inflearn.mini.workHub.team.domain.Team;

public record TeamInfoResponse(String name, String managerName, int employeeCount) {

    public static TeamInfoResponse from(final Team team) {
        return new TeamInfoResponse(
                team.getName(),
                team.getManagerName(),
                team.getEmployeeCount());
    }
}
