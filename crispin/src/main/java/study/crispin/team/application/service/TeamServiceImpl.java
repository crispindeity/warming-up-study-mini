package study.crispin.team.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.crispin.common.exception.VerificationException;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.repository.TeamRepository;
import study.crispin.team.presentation.response.TeamRegistrationResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponses;

import java.util.List;

import static study.crispin.common.exception.ExceptionMessage.TEAM_NAME_ALREADY_EXISTS;

@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public TeamRegistrationResponse registration(TeamRegistrationRequest request) {
        verifyDuplicateTeamName(request.name());

        Team team = Team.of(request.name());
        Team savedTeam = teamRepository.save(team);

        return new TeamRegistrationResponse(savedTeam.id(), savedTeam.name(), savedTeam.manager());
    }

    @Override
    public TeamRetrieveResponses retrieve() {
        List<TeamRetrieveResponse> responses = teamRepository.findAll().stream()
                .map(team -> TeamRetrieveResponse.of(
                        team.name(),
                        team.manager(),
                        memberRepository.countByTeamName(team.name())))
                .toList();
        return TeamRetrieveResponses.of(responses);
    }

    private void verifyDuplicateTeamName(String name) {
        if (teamRepository.existsByName(name)) {
            throw new VerificationException(TEAM_NAME_ALREADY_EXISTS);
        }
    }
}
