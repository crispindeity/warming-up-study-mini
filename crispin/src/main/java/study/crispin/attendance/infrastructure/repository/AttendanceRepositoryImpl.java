package study.crispin.attendance.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.entity.AttendanceEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepositoryImpl implements AttendanceRepository {

    private final JpaAttendanceRepository jpaAttendanceRepository;

    public AttendanceRepositoryImpl(JpaAttendanceRepository jpaAttendanceRepository) {
        this.jpaAttendanceRepository = jpaAttendanceRepository;
    }

    @Override
    public Attendance save(Attendance attendance) {
        return jpaAttendanceRepository.save(AttendanceEntity.fromModel(attendance))
                .toModel();
    }

    @Override
    public Optional<Attendance> findByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaAttendanceRepository.findByMemberIdAndDateRange(memberId, startDate, endDate)
                .map(AttendanceEntity::toModel);
    }

    @Override
    public List<Attendance> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaAttendanceRepository.findByMemberIdAndEndDateNotNullAndDateRange(memberId, startDate, endDate)
                .stream().map(AttendanceEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByMemberIdAndClockInDateTimeBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaAttendanceRepository.existsByMemberIdAndClockInDateTimeBetween(memberId, startDate, endDate);
    }
}
