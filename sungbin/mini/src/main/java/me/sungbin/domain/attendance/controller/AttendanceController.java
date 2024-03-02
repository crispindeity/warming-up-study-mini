package me.sungbin.domain.attendance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockInRequestDto;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockOutRequestDto;
import me.sungbin.domain.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.controller
 * @fileName : AttendanceController
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/clock-in")
    public void clockIn(@RequestBody @Valid AttendanceCreateClockInRequestDto requestDto) {
        this.attendanceService.clockIn(requestDto);
    }

    @PostMapping("/clock-out")
    public void clockOut(@RequestBody @Valid AttendanceCreateClockOutRequestDto requestDto) {
        this.attendanceService.clockOut(requestDto);
    }
}
