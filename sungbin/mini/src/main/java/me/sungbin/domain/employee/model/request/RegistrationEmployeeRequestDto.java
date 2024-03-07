package me.sungbin.domain.employee.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.sungbin.domain.employee.entity.Employee;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.model.request
 * @fileName : RegisterEmployeeRequestDto
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public record RegistrationEmployeeRequestDto(

        @NotBlank(message = "이름은 공란일 수 없습니다.")
        @NotNull(message = "이름은 null일 수 없습니다.")
        String name,

        @NotBlank(message = "등록하려는 팀의 이름이 공란일 수 없습니다.")
        @NotNull(message = "등록하려는 팀의 이름이 null일 수 없습니다.")
        String teamName,

        boolean isManager,

        @NotNull(message = "생일은 null일 수 없습니다.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthday
) {

    public Employee toEntity() {
        return Employee.builder()
                .name(name)
                .isManager(isManager)
                .birthday(birthday)
                .build();
    }
}
