package com.miniproject.commute.dto.member.response;

import com.miniproject.commute.domain.Role;

import java.time.LocalDate;
import java.util.Optional;

public record MemberResponse(String name, String teamName , Role role, LocalDate joinDate, LocalDate birthday) {
}
