package me.sungbin.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockInRequestDto;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockOutRequestDto;
import me.sungbin.domain.attendance.repository.AttendanceRepository;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.global.exception.custom.AlreadyAttendanceClockInException;
import me.sungbin.global.exception.custom.AlreadyAttendanceClockOutException;
import me.sungbin.global.exception.custom.AttendanceNotFoundException;
import me.sungbin.global.exception.custom.EmployeeNotFoundException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.service
 * @fileName : AttendanceService
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void clockIn(AttendanceCreateClockInRequestDto requestDto) {
        Employee employee = this.employeeRepository.findById(requestDto.employeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        Pair<LocalDateTime, LocalDateTime> todayRange = getTodayDateTimeRange();
        boolean existingAttendance = attendanceRepository
                .findByEmployeeAndClockInTimeBetween(employee, todayRange.getFirst(), todayRange.getSecond())
                .isPresent();

        if (existingAttendance) {
            throw new AlreadyAttendanceClockInException("이미 출근하셨습니다.");
        }

        Attendance attendance = requestDto.toEntity(employee);
        attendance.clockIn();
        this.attendanceRepository.save(attendance);
    }

    @Transactional
    public void clockOut(AttendanceCreateClockOutRequestDto requestDto) {
        // 직원 존재 여부 확인
        Employee employee = this.employeeRepository.findById(requestDto.employeeId())
                .orElseThrow(EmployeeNotFoundException::new);

        // 오늘의 날짜 범위 가져오기
        Pair<LocalDateTime, LocalDateTime> todayDateTimeRange = getTodayDateTimeRange();

        // 해당 직원의 오늘 날짜 기준 최근 출근 기록 조회
        Attendance attendance = this.attendanceRepository
                .findByEmployeeAndClockInTimeBetween(employee, todayDateTimeRange.getFirst(), todayDateTimeRange.getSecond())
                .orElseThrow(() -> new AttendanceNotFoundException("오늘의 출근 기록이 없습니다."));


        // 이미 퇴근 기록이 있는 경우 확인
        if (attendance.getClockOutTime() != null) {
            throw new AlreadyAttendanceClockOutException("이미 퇴근 처리가 되었습니다.");
        }

        // 퇴근 시간 기록
        attendance.clockOut();
    }
    private Pair<LocalDateTime, LocalDateTime> getTodayDateTimeRange() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return Pair.of(startOfDay, endOfDay);
    }

}
