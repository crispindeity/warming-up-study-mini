package me.sungbin.domain.annual.model.request;

import jakarta.validation.constraints.NotNull;
import me.sungbin.domain.annual.entity.AnnualLeave;
import me.sungbin.domain.employee.entity.Employee;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.model.request
 * @fileName : AnnualLeaveRequestDto
 * @date : 3/4/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/4/24       rovert         최초 생성
 */
public record AnnualLeaveRequestDto(

        @NotNull(message = "직원 ID는 null일 수 없습니다.")
        Long employeeId,

        @NotNull(message = "연차 사용일은 필수 입니다.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate leaveDate
) {

    public AnnualLeave toEntity(Employee employee) {
        return AnnualLeave.builder()
                .annualLeaveDate(leaveDate)
                .employee(employee)
                .build();
    }
}
