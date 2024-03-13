package inflearn.mini.annualleave.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.annualleave.domain.AnnualLeave;
import inflearn.mini.employee.domain.Employee;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Long> {

    boolean existsByEmployeeAndUseDate(final Employee employee, final LocalDate useDate);
}
