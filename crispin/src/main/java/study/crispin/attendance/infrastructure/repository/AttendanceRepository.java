package study.crispin.attendance.infrastructure.repository;

import study.crispin.attendance.domain.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);

    Optional<Attendance> findByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate);

    List<Attendance> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate);

    boolean existsByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate);
}
