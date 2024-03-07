package me.sungbin.domain.annual.repository;

import me.sungbin.domain.annual.entity.AnnualLeave;
import me.sungbin.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

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

    boolean existsByEmployeeAndAnnualLeaveDate(Employee employee, LocalDate annualDate);
}
