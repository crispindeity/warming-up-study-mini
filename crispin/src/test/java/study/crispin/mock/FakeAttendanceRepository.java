package study.crispin.mock;

import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;

import java.time.LocalDate;
import java.util.HashMap;
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

    public Optional<Attendance> findByMemberId(Long memberId) {
        return storage.values()
                .stream()
                .filter(attendance -> attendance.isMatchByMemberId(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByMemberIdAndDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        return storage.values()
                .stream()
                .anyMatch(attendance -> attendance.isTodayClockIn(memberId, startDate, endDate));
    }
}
