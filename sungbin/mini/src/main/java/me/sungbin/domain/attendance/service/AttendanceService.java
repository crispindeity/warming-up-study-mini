package me.sungbin.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.attendance.model.request.AttendanceCreateRequestDto;
import me.sungbin.domain.attendance.repository.AttendanceRepository;
import me.sungbin.domain.member.entity.Employee;
import me.sungbin.domain.member.repository.EmployeeRepository;
import me.sungbin.global.exception.custom.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void clockIn(AttendanceCreateRequestDto requestDto) {
        Employee employee = this.employeeRepository.findById(requestDto.id()).orElseThrow(EmployeeNotFoundException::new);
        Attendance attendance = requestDto.toEntity(employee);

        attendance.clockIn();
        this.attendanceRepository.save(attendance);
    }
}
