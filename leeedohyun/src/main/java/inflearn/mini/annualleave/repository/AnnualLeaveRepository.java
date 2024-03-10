package inflearn.mini.annualleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.annualleave.domain.AnnualLeave;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Long> {
}
