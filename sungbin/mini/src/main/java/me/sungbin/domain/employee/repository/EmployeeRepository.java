package me.sungbin.domain.employee.repository;

import me.sungbin.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.repository
 * @fileName : EmployeeRepository
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
