package inflearn.mini.employee.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import inflearn.mini.employee.domain.Employee;
import lombok.Builder;

@Builder
public record EmployeeRegisterRequestDto(
        @NotNull(message = "직원 이름은 필수입니다.")
        String employeeName,
        @NotNull(message = "팀 이름은 필수입니다.")
        String teamName,
        @NotNull(message = "매니저 여부는 필수입니다.")
        boolean isManager,
        @NotNull(message = "근무 시작일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate workStartDate,
        @NotNull(message = "생일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday) {

    public Employee toEntity() {
        return Employee.builder()
                .name(employeeName)
                .isManager(isManager)
                .workStartDate(workStartDate)
                .birthday(birthday)
                .build();
    }
}
