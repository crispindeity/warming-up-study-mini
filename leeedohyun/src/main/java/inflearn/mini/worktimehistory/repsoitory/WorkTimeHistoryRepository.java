package inflearn.mini.worktimehistory.repsoitory;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.worktimehistory.domain.WorkTimeHistory;

public interface WorkTimeHistoryRepository extends JpaRepository<WorkTimeHistory, Long> {

    boolean existsByEmployeeAndWorkEndTimeIsNull(final Employee employee);

    @Query("select w from WorkTimeHistory w where w.employee = :employee and w.workStartTime >= :workStartDate and w.workStartTime < :workEndDate and w.workEndTime is null")
    Optional<WorkTimeHistory> findByEmployeeAndWorkStartTimeAndAndWorkEndTimeIsNull(final Employee employee, final LocalDateTime workStartDate, final LocalDateTime workEndDate);
}
