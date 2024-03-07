package study.crispin.team.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.member.domain.Member;
import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.entity.TeamEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryImpl implements TeamRepository{

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
                .orElseThrow(() -> new IllegalArgumentException("소속된 팀이 없습니다.")).toModel();
        Team updatedTeam = findedTeam.updateManager(member);
        jpaTeamRepository.save(TeamEntity.fromModel(updatedTeam));
    }
}
