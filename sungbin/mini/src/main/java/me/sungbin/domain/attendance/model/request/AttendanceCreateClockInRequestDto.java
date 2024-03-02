package me.sungbin.domain.attendance.model.request;

import jakarta.validation.constraints.NotNull;
import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.employee.entity.Employee;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.model.request
 * @fileName : AttendanceCreateRequestDto
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public record AttendanceCreateClockInRequestDto(
        @NotNull(message = "직원 id는 null일 수 없습니다.")
        Long employeeId
) {

    public Attendance toEntity(Employee employee) {
        return Attendance.builder()
                .employee(employee)
                .build();
    }
}
