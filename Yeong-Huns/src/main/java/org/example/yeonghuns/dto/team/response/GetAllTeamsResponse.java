package org.example.yeonghuns.dto.team.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllTeamsResponse {
    private final String name;
    private final String manager;
    private final int memberCount;

    @Builder
    public GetAllTeamsResponse(String name, String manager, int memberCount) {
        this.name = name;
        this.manager = manager;
        this.memberCount = memberCount;
    }
}
