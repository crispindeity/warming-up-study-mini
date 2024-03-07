package study.crispin.attendance.infrastructure.repository;

import study.crispin.attendance.domain.Attendance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);

    Optional<Attendance> findByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    List<Attendance> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
}
