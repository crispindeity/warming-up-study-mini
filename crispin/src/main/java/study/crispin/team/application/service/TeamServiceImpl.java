package study.crispin.team.application.service;

import org.springframework.util.Assert;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.repository.TeamRepository;
import study.crispin.team.presentation.response.TeamRegistrationResponse;

public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamRegistrationResponse registration(TeamRegistrationRequest request) {
        Assert.notNull(request, "요청값은 필수입니다.");
        Assert.notNull(request.name(), "이름은 필수입니다.");

        verifyDuplicateTeamName(request.name());

        Team team = Team.of(request.name());
        Team savedTeam = repository.save(team);

        return new TeamRegistrationResponse(savedTeam.id(), savedTeam.name(), savedTeam.manager());
    }

    private void verifyDuplicateTeamName(String name) {
        Assert.notNull(name, "이름은 필수입니다.");

        if (repository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 팀 이름 입니다.");
        }
    }
}
