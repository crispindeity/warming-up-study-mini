package me.sungbin.domain.attendance.model.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.model.request
 * @fileName : AttendanceCreateClockOutRequestDto
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public record AttendanceCreateClockOutRequestDto(
        @NotNull(message = "직원 id는 null일 수 없습니다.")
        Long employeeId
) {
}
