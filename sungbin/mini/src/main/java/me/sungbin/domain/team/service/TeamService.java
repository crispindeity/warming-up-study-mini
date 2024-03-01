package me.sungbin.domain.team.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.model.request.RegisterTeamRequestDto;
import me.sungbin.domain.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.service
 * @fileName : TeamService
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void registerTeam(RegisterTeamRequestDto requestDto) {
        Team team = requestDto.toEntity();

        this.teamRepository.save(team);
    }
}
