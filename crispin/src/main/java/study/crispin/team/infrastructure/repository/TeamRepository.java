package study.crispin.team.infrastructure.repository;

import study.crispin.team.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);

    Optional<Team> findByName(String name);

    List<Team> findAll();
}
