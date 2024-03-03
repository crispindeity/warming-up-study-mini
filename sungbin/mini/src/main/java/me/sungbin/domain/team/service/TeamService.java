package me.sungbin.domain.team.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.model.request.RegisterTeamRequestDto;
import me.sungbin.domain.team.model.response.FindTeamInfoResponseDto;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.exception.custom.TeamAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        validateTeam(requestDto); // 유효성 검사

        Team team = requestDto.toEntity(); // dto to entity

        this.teamRepository.save(team);
    }

    public List<FindTeamInfoResponseDto> findTeamInfo() {
        List<Team> teams = this.teamRepository.findAll();

        return teams.stream()
                .map(FindTeamInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 팀 유효성 검사
     * @param requestDto
     */
    private void validateTeam(RegisterTeamRequestDto requestDto) {
        if (this.teamRepository.existsByName(requestDto.name())) {
            throw new TeamAlreadyExistsException("이미 존재하는 팀 이름입니다.");
        }
    }
}
