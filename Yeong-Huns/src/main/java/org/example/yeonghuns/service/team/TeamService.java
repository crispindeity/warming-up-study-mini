package org.example.yeonghuns.service.team;

import lombok.RequiredArgsConstructor;
import org.example.yeonghuns.config.Error.exception.member.MemberNotFoundException;
import org.example.yeonghuns.config.Error.exception.team.TeamAlreadyExistsException;
import org.example.yeonghuns.config.Error.exception.team.TeamNotFoundException;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.domain.Team;
import org.example.yeonghuns.dto.member.request.SaveMemberRequest;
import org.example.yeonghuns.dto.team.request.CreateTeamRequest;
import org.example.yeonghuns.dto.team.request.UpdateDayBeforeAnnualRequest;
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
        if (teamRepository.existsByName(request.name())) throw new TeamAlreadyExistsException();
        teamRepository.save(request.toEntity());
    }

    public Team findTeamByName(SaveMemberRequest request) {
        return teamRepository.findByName(request.teamName()).orElseThrow(TeamNotFoundException::new);
    }
    @Transactional
    public void updateManager(Team team, Member member) {
        if (team.getManager() == null) team.updateManager(member.getName());
        else {
            Member previous_Manager = memberRepository.findByTeamIdAndRoleIsTrueAndIdNot(team.getId(), member.getId())
                    .orElseThrow(MemberNotFoundException::new);
            previous_Manager.changeRole();
            team.updateManager(member.getName());
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateDayBeforeAnnual(UpdateDayBeforeAnnualRequest request){
        Team team = teamRepository.findById(request.id()).orElseThrow(TeamNotFoundException::new);
        team.updateDayBeforeAnnual(request.day());
    }

    @Transactional(readOnly = true)
    public List<GetAllTeamsResponse> getAllTeams() {
        return teamRepository.findAll().stream().map(GetAllTeamsResponse::from).toList();
    }
}
