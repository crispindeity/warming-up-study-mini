package me.sungbin.domain.team.repository;

import me.sungbin.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.repository
 * @fileName : TeamRepository
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    Optional<Team> findByName(String name);
}
