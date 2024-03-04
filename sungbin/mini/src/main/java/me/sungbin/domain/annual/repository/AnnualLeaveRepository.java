package me.sungbin.domain.annual.repository;

import me.sungbin.domain.annual.entity.AnnualLeave;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.repository
 * @fileName : AnnualLeaveRepository
 * @date : 3/4/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/4/24       rovert         최초 생성
 */
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Long> {
}
