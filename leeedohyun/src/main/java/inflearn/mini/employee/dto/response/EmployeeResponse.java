package inflearn.mini.employee.dto.response;

import java.time.LocalDate;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.domain.Role;
import lombok.Builder;

@Builder
public record EmployeeResponse(String employeeName,
                               String teamName,
                               Role role,
                               LocalDate birthday,
                               LocalDate workStartDate) {

    public static EmployeeResponse from(final Employee employee) {
        return EmployeeResponse.builder()
                .employeeName(employee.getName())
                .teamName(employee.getTeam().getName())
                .role(employee.getRole())
                .birthday(employee.getBirthday())
                .workStartDate(employee.getWorkStartDate())
                .build();
    }
}
