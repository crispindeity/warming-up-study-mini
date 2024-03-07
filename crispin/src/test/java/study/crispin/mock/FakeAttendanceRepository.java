package study.crispin.mock;

import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeAttendanceRepository implements AttendanceRepository {

    private final Map<Long, Attendance> storage = new HashMap<>();
    private Long sequence = 0L;

    public Attendance save(Attendance attendance) {
        if (attendance.id() == null || storage.get(attendance.id()) == null) {
            Attendance newAttendance = Attendance.of(
                    ++sequence,
                    attendance.member(),
                    attendance.clockInDateTime()
            );
            storage.put(sequence, newAttendance);
            return storage.get(sequence);
        } else {
            storage.put(sequence, attendance);
            return storage.get(attendance.id());
        }
    }

    @Override
    public List<Attendance> findByMemberIdAndEndDateNotNullAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return storage.values()
                .stream()
                .filter(attendance ->
                        attendance.isClockInAndOut(memberId, startDate, endDate))
                .toList();
    }

    @Override
    public Optional<Attendance> findByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return storage.values()
                .stream()
                .filter(attendance ->
                        attendance.isClockIn(memberId, startDate, endDate))
                .reduce((first, second) -> second);
    }

    @Override
    public boolean existsByMemberIdAndDateRange(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return storage.values()
                .stream()
                .anyMatch(attendance -> attendance.isClockIn(memberId, startDate, endDate));
    }
}
