package org.example.yeonghuns.dto.member.request;

import lombok.Getter;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.domain.Team;

import java.time.LocalDate;

@Getter
public class SaveMemberRequest {
    private String name;
    private String teamname;
    private Boolean isManager;
    private LocalDate birthday;

    public Member toEntity(Team team) {
        return Member.builder()
                .name(this.name)
                .teamName(team.getName())
                .role(isManager)
                .birthday(this.birthday)
                .team(team)
                .build();
    }
}
