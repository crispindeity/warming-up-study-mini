package inflearn.mini.employee.dto.request;

import java.time.LocalDate;

import inflearn.mini.employee.domain.Employee;
import lombok.Builder;

@Builder
public record EmployeeRegisterRequestDto(String employeeName,
                                         String teamName,
                                         boolean isManager,
                                         LocalDate workStartDate,
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
