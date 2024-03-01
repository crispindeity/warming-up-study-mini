package com.miniproject.commute.dto.team.response;

import com.miniproject.commute.domain.Team;

public record TeamMemberCountResponse(long id, String name, long memberCount) {
}
