package study.crispin.attendance.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.attendance.domain.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepositoryImpl implements AttendanceRepository {

    @Override
    public Attendance save(Attendance attendance) {
        return null;
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
