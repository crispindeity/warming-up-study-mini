package org.example.yeonghuns.dto.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class GetAllMembersResponse {
    public final String name;
    public final String teamName;
    public final String role;
    public final LocalDate birthday;
    public final LocalDate workStartDate;

    @Builder
    public GetAllMembersResponse(String name, String teamName, String role, LocalDate birthday, LocalDateTime workStartDate) {
        this.name = name;
        this.teamName = teamName;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate.toLocalDate();
    }
}
