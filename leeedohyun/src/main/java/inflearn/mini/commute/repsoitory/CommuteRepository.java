package inflearn.mini.commute.repsoitory;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import inflearn.mini.commute.domain.Commute;
import inflearn.mini.employee.domain.Employee;

public interface CommuteRepository extends JpaRepository<Commute, Long> {

    boolean existsByEmployeeAndWorkEndTimeIsNull(final Employee employee);

    @Query("select w from Commute w where w.employee = :employee "
            + "and w.workStartTime >= :workStartDate and w.workStartTime < :workEndDate "
            + "and w.workEndTime is null"
    )
    Optional<Commute> findCommuteForDate(final Employee employee, final LocalDateTime workStartDate, final LocalDateTime workEndDate);

    Optional<Commute> findByEmployeeAndWorkStartTimeBetween(final Employee employee, final LocalDateTime workStartDate, final LocalDateTime workEndDate);
}
