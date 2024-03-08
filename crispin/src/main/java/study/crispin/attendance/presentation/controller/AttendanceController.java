package study.crispin.attendance.presentation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.crispin.attendance.application.AttendanceService;
import study.crispin.attendance.application.request.ClockInOrOutRequest;
import study.crispin.attendance.application.request.WorkHoursInquiryRequest;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponses;
import study.crispin.common.DateTimeHolder;

@RestController
@RequestMapping("/api/v1")
public class AttendanceController {

    private final DateTimeHolder dateTimeHolder;
    private final AttendanceService attendanceService;

    public AttendanceController(DateTimeHolder dateTimeHolder, AttendanceService attendanceService) {
        this.dateTimeHolder = dateTimeHolder;
        this.attendanceService = attendanceService;
    }

    @PostMapping("/clock-in")
    public ResponseEntity<ClockInResponse> clockIn(
            @RequestBody ClockInOrOutRequest request
    ) {
        return ResponseEntity.ok(attendanceService.clockIn(request.memberId(), dateTimeHolder.now()));
    }

    @PostMapping("/clock-out")
    public ResponseEntity<ClockOutResponse> clockOut(
            @RequestBody ClockInOrOutRequest request
    ) {
        return ResponseEntity.ok(attendanceService.clockOut(request.memberId(), dateTimeHolder.now()));
    }

    @GetMapping("/work-hours")
    public ResponseEntity<WorkHoursInquiryResponses> inquiry(
            @Valid WorkHoursInquiryRequest request
    ) {
        return ResponseEntity.ok(attendanceService.workHoursInquiry(request));
    }
}
