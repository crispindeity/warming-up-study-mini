package study.crispin.team.application.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.repository.TeamRepository;
import study.crispin.team.presentation.response.TeamRegistrationResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponses;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public TeamRegistrationResponse registration(TeamRegistrationRequest request) {
        Assert.notNull(request, "요청값은 필수입니다.");
        Assert.notNull(request.name(), "이름은 필수입니다.");

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
        Assert.notNull(name, "이름은 필수입니다.");

        if (teamRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 팀 이름 입니다.");
        }
    }
}
