package study.crispin.attendance.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.crispin.attendance.application.AttendanceService;
import study.crispin.attendance.application.request.ClockInOrOutRequest;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
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
        ClockInResponse response = attendanceService.clockIn(request.memberId(), dateTimeHolder.now());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/clock-out")
    public ResponseEntity<ClockOutResponse> clockOut(
            @RequestBody ClockInOrOutRequest request
    ) {
        ClockOutResponse response = attendanceService.clockOut(request.memberId(), dateTimeHolder.now());
        return ResponseEntity.ok().body(response);
    }
}
