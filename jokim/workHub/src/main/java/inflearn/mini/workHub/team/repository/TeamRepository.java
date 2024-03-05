package inflearn.mini.workHub.team.repository;

import inflearn.mini.workHub.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
    boolean existsByName(String name);
}
