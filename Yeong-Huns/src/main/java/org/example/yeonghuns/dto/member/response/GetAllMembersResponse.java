package org.example.yeonghuns.dto.member.response;

import lombok.Builder;
import org.example.yeonghuns.domain.Member;

import java.time.LocalDate;


@Builder
public record GetAllMembersResponse(String name, String teamName, String role, LocalDate birthday,
                                    LocalDate workStartDate) {

    public static GetAllMembersResponse from(Member member){
        String isManager = member.isRole() ? "MANAGER" : "MEMBER";

        return GetAllMembersResponse.builder()
                .name(member.getName())
                .teamName(member.getTeam().getName())
                .role(isManager)
                .birthday(member.getBirthday())
                .workStartDate(member.getWorkStartDate().toLocalDate())
                .build();
    }
}
