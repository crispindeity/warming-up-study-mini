package study.crispin.team.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.common.exception.ExceptionMessage;
import study.crispin.common.exception.NotFoundException;
import study.crispin.member.domain.Member;
import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.entity.TeamEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final JpaTeamRepository jpaTeamRepository;

    public TeamRepositoryImpl(JpaTeamRepository jpaTeamRepository) {
        this.jpaTeamRepository = jpaTeamRepository;
    }

    @Override
    public Team save(Team team) {
        return jpaTeamRepository.save(TeamEntity.fromModel(team))
                .toModel();
    }

    @Override
    public Optional<Team> findByName(String name) {
        return jpaTeamRepository.findByName(name)
                .map(teamEntity -> TeamEntity.toModel(teamEntity));
    }

    @Override
    public List<Team> findAll() {
        return jpaTeamRepository.findAll()
                .stream()
                .map(teamEntity -> TeamEntity.toModel(teamEntity))
                .toList();
    }

    @Override
    public void updateTeamManager(Member member) {
        Team findedTeam = jpaTeamRepository.findByName(member.teamName())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.TEAM_NAME_ALREADY_EXISTS))
                .toModel();
        Team updatedTeam = findedTeam.updateManager(member);
        jpaTeamRepository.save(TeamEntity.fromModel(updatedTeam));
    }

    @Override
    public boolean existsByName(String name) {
        return jpaTeamRepository.existsByName(name);
    }
}
