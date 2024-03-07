package study.crispin.attendance.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.crispin.attendance.application.request.WorkHoursInquiryRequest;
import study.crispin.attendance.domain.Attendance;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponses;
import study.crispin.common.LocalDateUtil;
import study.crispin.common.exception.NotFoundException;
import study.crispin.common.exception.VerificationException;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static study.crispin.common.exception.ExceptionMessage.*;

@Service
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(
            MemberRepository memberRepository,
            AttendanceRepository attendanceRepository
    ) {
        this.memberRepository = memberRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    @Transactional
    public ClockInResponse clockIn(Long memberId, LocalDateTime clockInDateTime) {
        verifyTodayClockIn(memberId, clockInDateTime);

        Member findedMember = findMemberById(memberId);

        Attendance attendance = Attendance.clockIn(findedMember, clockInDateTime);
        Attendance savedAttendance = attendanceRepository.save(attendance);

        return ClockInResponse.of(
                savedAttendance.id(), savedAttendance.member().name(), savedAttendance.clockInDateTime()
        );
    }

    @Override
    @Transactional
    public ClockOutResponse clockOut(Long memberId, LocalDateTime clockOutDateTime) {
        LocalDateTime startDate = LocalDateUtil.convertToDateTwoDaysAgo(clockOutDateTime);
        LocalDateTime endDate = LocalDateUtil.convertToDateOneDayLater(clockOutDateTime);

        Attendance findedAttendance = attendanceRepository.findByMemberIdAndDateRange(memberId, startDate, endDate)
                .orElseThrow(() -> new NotFoundException(NOT_CLOCKED_IN));

        Attendance clockOutedAttendance = Attendance.clockOut(findedAttendance, clockOutDateTime);
        attendanceRepository.save(clockOutedAttendance);

        return ClockOutResponse.of(
                clockOutedAttendance.id(),
                clockOutedAttendance.member().name(),
                clockOutedAttendance.clockInDateTime(),
                clockOutedAttendance.clockOutDateTime()
        );
    }

    @Override
    public WorkHoursInquiryResponses workHoursInquiry(WorkHoursInquiryRequest request) {
        verifyRegisteredMember(request.getMemberId());

        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();

        List<WorkHoursInquiryResponse> workHoursInquiryResponses = findWorkHoursInquiry(request, startDate, endDate);
        long totalWorkMinute = calculateWorkMinutes(workHoursInquiryResponses);

        return WorkHoursInquiryResponses.of(workHoursInquiryResponses, totalWorkMinute);
    }

    private void verifyTodayClockIn(Long memberId, LocalDateTime clockInDateTime) {
        LocalDateTime startDate = LocalDateUtil.convertToDateOneDaysAgo(clockInDateTime);
        LocalDateTime endDate = LocalDateUtil.convertToDateOneDayLater(clockInDateTime);

        if (attendanceRepository.existsByMemberIdAndDateRange(memberId, startDate, endDate)) {
            throw new VerificationException(ALREADY_CLOCKED_IN);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(UNREGISTERED_MEMBER));
    }

    private void verifyRegisteredMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new VerificationException(UNREGISTERED_MEMBER);
        }
    }

    private List<WorkHoursInquiryResponse> findWorkHoursInquiry(WorkHoursInquiryRequest request, LocalDateTime startDate, LocalDateTime endDate) {
        return attendanceRepository
                .findByMemberIdAndEndDateNotNullAndDateRange(request.getMemberId(), startDate, endDate)
                .stream()
                .map(attendance -> {
                    LocalDateTime clockInDate = attendance.clockInDateTime();
                    Long workHour = attendance.calculateWorkHour();
                    return WorkHoursInquiryResponse.of(clockInDate, workHour);
                }).toList();
    }

    private long calculateWorkMinutes(List<WorkHoursInquiryResponse> workHoursInquiryResponses) {
        return workHoursInquiryResponses.stream()
                .mapToLong(WorkHoursInquiryResponse::workingMinutes)
                .sum();
    }
}
