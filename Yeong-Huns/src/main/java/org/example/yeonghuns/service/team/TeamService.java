package org.example.yeonghuns.service.team;

import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.domain.Team;
import org.example.yeonghuns.dto.member.request.SaveMemberRequest;
import org.example.yeonghuns.dto.team.request.CreateTeamRequest;
import org.example.yeonghuns.dto.team.response.GetAllTeamsResponse;
import org.example.yeonghuns.repository.MemberRepository;
import org.example.yeonghuns.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createTeam(CreateTeamRequest request) {
        if (teamRepository.existsByName(request.getName())) throw new IllegalArgumentException("존재하는 팀입니다.");
        teamRepository.save(request.toEntity());
    }

    public Team findTeamByName(SaveMemberRequest request) {
        Team team = teamRepository.findByName(request.getTeamname()).orElseThrow(IllegalArgumentException::new);
        if (request.getIsManager()) {
            updateManager(team, request.getName());
        }
        return team;
    }

    public void updateManager(Team team, String managerName) {
        if (team.getManager() == null) team.updateManager(managerName);
        else {
            Member member = memberRepository.findByTeamIdAndRoleIsTrue(team.getId())
                    .orElseThrow(IllegalArgumentException::new); //여기서 왜 memberRepository를 받았는지
            member.changeRole();
            team.updateManager(managerName);
        }
    }

    public List<GetAllTeamsResponse> getAllTeams() {
        return teamRepository.findAll().stream().map(Team::toResponse).toList();
    }
}
