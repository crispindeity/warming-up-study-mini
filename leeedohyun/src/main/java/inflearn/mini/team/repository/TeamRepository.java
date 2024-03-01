package inflearn.mini.team.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.team.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(final String teamName);
}
