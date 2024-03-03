package me.sungbin.domain.attendance.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.model.request
 * @fileName : WorkTimeSummaryRequestDto
 * @date : 3/3/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/3/24       rovert         최초 생성
 */
public record WorkTimeSummaryRequestDto(

        @NotNull(message = "직원 id는 null일 수 없습니다.")
        Long employId,

        @Pattern(regexp = "\\d{4}-\\d{2}", message = "날짜는 YYYY-MM 형식이어야 합니다.")
        String date
) {
}
