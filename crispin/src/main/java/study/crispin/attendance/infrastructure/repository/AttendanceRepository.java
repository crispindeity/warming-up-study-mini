package study.crispin.attendance.infrastructure.repository;

import study.crispin.attendance.domain.Attendance;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);

    Optional<Attendance> findByMemberId(Long memberId);

    boolean existsByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate);
}
