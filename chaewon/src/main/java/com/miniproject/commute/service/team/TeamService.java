package com.miniproject.commute.service.team;

import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.Team;
import com.miniproject.commute.dto.team.request.TeamSaveRequest;
import com.miniproject.commute.dto.team.response.TeamGetResponse;
import com.miniproject.commute.dto.team.response.TeamMemberCountResponse;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveTeam(TeamSaveRequest request) {
        teamRepository.save(Team.builder().name(request.name()).build());
    }

    @Transactional(readOnly = true)
    public List<TeamGetResponse> getTeams() {
        List<TeamMemberCountResponse> allTeamAndMemberCount = teamRepository.findAllTeamAndMemberCount();
        System.out.println("logic");
        return allTeamAndMemberCount.stream()
                .map(subDto ->
                {
                    String managerName = memberRepository.findManagerByTeamId(subDto.id()).orElse(null);
                    return new TeamGetResponse(subDto.name(), managerName, subDto.memberCount());
                }).collect(Collectors.toList());
    }
}
