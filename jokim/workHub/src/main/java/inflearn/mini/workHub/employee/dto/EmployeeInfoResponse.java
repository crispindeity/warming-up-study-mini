package inflearn.mini.workHub.employee.dto;

import inflearn.mini.workHub.employee.domain.Employee;
import inflearn.mini.workHub.employee.domain.Role;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record EmployeeInfoResponse(String name, String teamName, Role role, LocalDate birthday, LocalDate workStartDt) {

    public static EmployeeInfoResponse from(final Employee employee){
        return EmployeeInfoResponse.builder()
                .name(employee.getName())
                .teamName(employee.getTeam().getName())
                .role(employee.getRole())
                .birthday(employee.getBirthday())
                .workStartDt(employee.getWorkStartDt())
                .build();
    }
}
