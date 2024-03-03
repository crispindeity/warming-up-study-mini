package study.crispin.team.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.crispin.team.infrastructure.entity.TeamEntity;

import java.util.Optional;

public interface JpaTeamRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByName(String name);
}
