package org.example.yeonghuns.dto.team.request;

import lombok.Getter;
import org.example.yeonghuns.domain.Team;

@Getter
public class CreateTeamRequest {
    private String name;

    public Team toEntity(){
        return Team.builder()
                .name(name)
                .build();
    }
}
