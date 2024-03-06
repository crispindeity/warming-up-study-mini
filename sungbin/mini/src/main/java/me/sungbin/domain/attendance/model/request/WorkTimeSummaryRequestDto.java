package me.sungbin.domain.attendance.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;

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
        Long employeeId,

        @DateTimeFormat(pattern = "yyyy-MM")
        YearMonth date
) {
}
