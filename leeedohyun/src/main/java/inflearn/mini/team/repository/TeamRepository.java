package inflearn.mini.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.team.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
