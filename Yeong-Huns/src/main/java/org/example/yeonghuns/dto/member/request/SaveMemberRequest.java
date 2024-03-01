package org.example.yeonghuns.dto.member.request;

import lombok.Getter;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.domain.Team;

import java.time.LocalDate;


public record SaveMemberRequest (String name, String teamName, Boolean isManager, LocalDate birthday){
    public Member toEntity(Team team) {
        return Member.builder()
                .name(this.name)
                .role(isManager)
                .birthday(this.birthday)
                .team(team)
                .build();
    }
}
