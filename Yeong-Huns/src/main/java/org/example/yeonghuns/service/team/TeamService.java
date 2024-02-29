package org.example.yeonghuns.service.team;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createTeam(CreateTeamRequest request) {
        if (teamRepository.existsByName(request.name())) throw new IllegalArgumentException("존재하는 팀입니다.");
        teamRepository.save(request.toEntity());
    }

    public Team findTeamByName(SaveMemberRequest request) {
        return teamRepository.findByName(request.teamName()).orElseThrow(IllegalArgumentException::new);
    }
    @Transactional
    public void updateManager(Team team, Member member) {
        if (team.getManager() == null) team.updateManager(member.getName());
        else {
            Member previous_Manager = memberRepository.findByTeamIdAndRoleIsTrueAndIdNot(team.getId(), member.getId())
                    .orElseThrow(IllegalArgumentException::new);
            previous_Manager.changeRole();
            team.updateManager(member.getName());
        }
        teamRepository.save(team);
    }

    @Transactional(readOnly = true)
    public List<GetAllTeamsResponse> getAllTeams() {
        return teamRepository.findAll().stream().map(GetAllTeamsResponse::from).toList();
    }
}
