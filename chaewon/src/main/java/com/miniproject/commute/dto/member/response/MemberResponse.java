package com.miniproject.commute.dto.member.response;

import com.miniproject.commute.domain.Role;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Optional;

public record MemberResponse(String name, String teamName , Role role, LocalDate joinDate, LocalDate birthday) {
    @Builder
    public MemberResponse(String name, String teamName, Role role, LocalDate joinDate, LocalDate birthday) {
        this.name = name;
        this.teamName = teamName;
        this.role = role;
        this.joinDate = joinDate;
        this.birthday = birthday;
    }
}
