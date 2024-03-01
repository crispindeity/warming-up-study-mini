package com.miniproject.commute.dto.member.request;

import java.time.LocalDate;

public record MemberSaveRequest(String name, boolean isManager, LocalDate joinDate, LocalDate birthday, Long teamId) {
}
