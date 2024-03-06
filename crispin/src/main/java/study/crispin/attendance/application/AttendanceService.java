package study.crispin.attendance.application;

import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.common.LocalDateUtil;
import study.crispin.common.exception.NotFoundException;
import study.crispin.common.exception.VerificationException;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static study.crispin.common.exception.ExceptionMessage.*;

public class AttendanceService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(
            MemberRepository memberRepository,
            AttendanceRepository attendanceRepository
    ) {
        this.memberRepository = memberRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public ClockInResponse clockIn(Long memberId, LocalDateTime clockInDateTime) {
        verifyTodayClockIn(memberId, clockInDateTime);

        Member findedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(UNREGISTERED_MEMBER));

        Attendance attendance = Attendance.clockIn(findedMember, clockInDateTime);
        Attendance savedAttendance = attendanceRepository.save(attendance);

        return ClockInResponse.of(
                savedAttendance.id(), savedAttendance.member().name(), savedAttendance.clockInDateTime()
        );
    }

    public ClockOutResponse clockOut(Long memberId, LocalDateTime clockOutDateTime) {
        LocalDate startDate = LocalDateUtil.convertToDateTwoDaysAgo(clockOutDateTime);
        LocalDate endDate = LocalDateUtil.convertToDateOneDayLater(clockOutDateTime);

        Attendance findedAttendance = attendanceRepository.findByMemberIdAndDateRange(memberId, startDate, endDate)
                .orElseThrow(() -> new NotFoundException(NOT_CLOCKED_IN));

        Attendance clockOutedAttendance = Attendance.clockOut(findedAttendance, clockOutDateTime);
        Attendance savedAttendance = attendanceRepository.save(clockOutedAttendance);

        return ClockOutResponse.of(
                savedAttendance.id(),
                savedAttendance.member().name(),
                savedAttendance.clockInDateTime(),
                savedAttendance.clockOutDateTime()
        );
    }

    private void verifyTodayClockIn(Long memberId, LocalDateTime clockInDateTime) {
        LocalDate startDate = LocalDateUtil.convertToDateOneDaysAgo(clockInDateTime);
        LocalDate endDate = LocalDateUtil.convertToDateOneDayLater(clockInDateTime);

        if (attendanceRepository.existsByMemberIdAndDateRange(memberId, startDate, endDate)) {
            throw new VerificationException(ALREADY_CLOCKED_IN);
        }
    }
}
