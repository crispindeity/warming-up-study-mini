package org.example.yeonghuns.dto.team.response;

import lombok.Builder;
import org.example.yeonghuns.domain.Team;

@Builder
public record GetAllTeamsResponse(String name, String manager, int memberCount) {
    public static GetAllTeamsResponse from(Team team) {
        return GetAllTeamsResponse.builder()
                .name(team.getName())
                .manager(team.getManager())
                .memberCount(team.getMemberList().size())
                .build();
    }
}
