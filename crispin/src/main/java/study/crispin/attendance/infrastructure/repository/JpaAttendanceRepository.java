package study.crispin.attendance.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.crispin.attendance.infrastructure.entity.AttendanceEntity;

public interface JpaAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
}
