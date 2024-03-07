package com.miniproject.commute.service.team;

import com.miniproject.commute.domain.Team;
import com.miniproject.commute.dto.team.request.TeamSaveRequest;
import com.miniproject.commute.dto.team.response.TeamGetResponse;
import com.miniproject.commute.dto.team.response.TeamMemberCountResponse;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTeam(TeamSaveRequest request) {
        teamRepository.save(Team.builder()
                .name(request.name())
                .dayOffPeriod(request.dayOffPeriod())
                .build());
    }

    @Transactional(readOnly = true)
    public List<TeamGetResponse> getTeams() {
        List<TeamMemberCountResponse> allTeamAndMemberCount = teamRepository.findAllTeamAndMemberCount();
        return allTeamAndMemberCount.stream()
                .map(subDto ->
                {
                    String managerName = memberRepository.findManagerByTeamId(subDto.id()).orElse(null);
                    return new TeamGetResponse(subDto.name(), managerName, subDto.memberCount());
                }).toList();
    }
}
