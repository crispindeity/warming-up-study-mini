package me.sungbin.domain.team.model.response;

import me.sungbin.domain.team.entity.Team;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.model.response
 * @fileName : FindTeamInfoResponseDto
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public record FindTeamInfoResponseDto(String name, String manager, int memberCount) {

    public FindTeamInfoResponseDto(Team team) {
        this(team.getName(), team.getManagerName(), team.getEmployeeCount());
    }
}
