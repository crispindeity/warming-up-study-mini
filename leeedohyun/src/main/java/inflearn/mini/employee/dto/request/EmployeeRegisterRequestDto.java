package inflearn.mini.employee.dto.request;

import java.time.LocalDate;

import inflearn.mini.employee.domain.Employee;
import lombok.Builder;

@Builder
public record EmployeeRegisterRequestDto(String employeeName,
                                         boolean isManager,
                                         LocalDate workStartDate,
                                         LocalDate birthday) {

    public Employee toEntity() {
        return new Employee(employeeName, isManager, workStartDate, birthday);
    }
}
