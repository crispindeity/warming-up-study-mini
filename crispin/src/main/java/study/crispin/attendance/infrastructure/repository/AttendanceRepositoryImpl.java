package study.crispin.attendance.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.entity.AttendanceEntity;

import java.time.LocalDate;
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
        return jpaAttendanceRepository.save(AttendanceEntity.fromModel(attendance)).toModel();
    }

    @Override
    public Optional<Attendance> findByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        return Optional.empty();
    }

    @Override
    public List<Attendance> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public boolean existsByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
