package study.crispin.attendance.application;

import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.common.DateTimeHolder;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;

import java.time.LocalDateTime;

public class AttendanceService {

    private final DateTimeHolder dateTimeHolder;
    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(
            DateTimeHolder dateTimeHolder,
            MemberRepository memberRepository,
            AttendanceRepository attendanceRepository
    ) {
        this.dateTimeHolder = dateTimeHolder;
        this.memberRepository = memberRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public ClockInResponse clockIn(Long memberId) {
        LocalDateTime clockInDateTime = dateTimeHolder.now();
        Member findedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(""));
        Attendance attendance = Attendance.clockIn(findedMember, clockInDateTime);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return ClockInResponse.of(
                savedAttendance.id(), savedAttendance.member().name(), savedAttendance.clockInDateTime()
        );
    }

    public ClockOutResponse clockOut(Long memberId) {
        LocalDateTime clockOutDateTime = dateTimeHolder.now().plusHours(9L);
        Attendance findedAttendance = attendanceRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException(""));
        Attendance clockOutedAttendance = Attendance.clockOut(findedAttendance, clockOutDateTime);
        Attendance savedAttendance = attendanceRepository.save(clockOutedAttendance);
        return ClockOutResponse.of(
                savedAttendance.id(),
                savedAttendance.member().name(),
                savedAttendance.clockInDateTime(),
                savedAttendance.clockOutDateTime()
        );
    }
}
