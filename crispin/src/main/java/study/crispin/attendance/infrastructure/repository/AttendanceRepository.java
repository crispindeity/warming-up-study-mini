package study.crispin.attendance.infrastructure.repository;

import study.crispin.attendance.domain.Attendance;

import java.util.Optional;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);

    Optional<Attendance> findByMemberId(Long memberId);
}
