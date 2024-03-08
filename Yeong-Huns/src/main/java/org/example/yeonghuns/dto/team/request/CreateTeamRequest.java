package org.example.yeonghuns.dto.team.request;

import lombok.Getter;
import org.example.yeonghuns.domain.Team;


public record CreateTeamRequest (String name){
    public Team toEntity(){
        return Team.builder()
                .name(this.name)
                .dayBeforeAnnual(0)
                .build();
    }
}
