package study.crispin.attendance.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.crispin.attendance.infrastructure.entity.AttendanceEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    @Query(value = "select id, member_id, clock_in_date_time, clock_out_date_time " +
            "from attendances " +
            "where member_id = :memberId " +
            "and clock_in_date_time >= :startDate " +
            "and clock_in_date_time < :endDate " +
            "and clock_out_date_time is null " +
            "order by clock_in_date_time desc " +
            "limit 1", nativeQuery = true)
    Optional<AttendanceEntity> findByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select ae " +
            "from AttendanceEntity ae " +
            "where ae.member.id = :memberId " +
            "and ae.clockOutDateTime is not null " +
            "and ae.clockInDateTime >= :startDate " +
            "and ae.clockInDateTime < :endDate")
    List<AttendanceEntity> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByMemberIdAndClockInDateTimeBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
}
