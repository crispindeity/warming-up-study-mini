package study.crispin.attendance.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.crispin.attendance.infrastructure.entity.AttendanceEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    @Query(
            value = "select * " +
                    "from attendances " +
                    "where member_id = :memberId " +
                    "and clock_in_date_time > :startDate " +
                    "and clock_in_date_time < :endDate " +
                    "and clock_out_date_time is null " +
                    "order by clock_in_date_time desc " +
                    "limit 1",
            nativeQuery = true
    )
    Optional<AttendanceEntity> findByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate);
}
