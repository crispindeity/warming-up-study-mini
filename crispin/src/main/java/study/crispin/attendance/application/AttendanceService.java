package study.crispin.attendance.application;

import study.crispin.attendance.application.request.WorkHoursInquiryRequest;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponses;

import java.time.LocalDateTime;

public interface AttendanceService {

    ClockInResponse clockIn(Long memberId, LocalDateTime clockInDateTime);

    ClockOutResponse clockOut(Long memberId, LocalDateTime clockOutDateTime);

    WorkHoursInquiryResponses workHoursInquiry(WorkHoursInquiryRequest request);
}
