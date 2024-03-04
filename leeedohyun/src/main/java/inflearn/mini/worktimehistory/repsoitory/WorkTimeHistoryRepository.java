package inflearn.mini.worktimehistory.repsoitory;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.worktimehistory.domain.WorkTimeHistory;

public interface WorkTimeHistoryRepository extends JpaRepository<WorkTimeHistory, Long> {

    boolean existsByEmployeeAndWorkEndTimeIsNull(final Employee employee);
}
