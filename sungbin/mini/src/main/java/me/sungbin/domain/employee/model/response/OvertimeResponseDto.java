package me.sungbin.domain.employee.model.response;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.employee.model.response
 * @fileName : OvertimeResponseDto
 * @date : 3/5/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/5/24       rovert         최초 생성
 */
public record OvertimeResponseDto(Long id, String name, int overtimeMinutes) {
}
