package me.sungbin.domain.team.repository;

import me.sungbin.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
