package inflearn.mini.workHub.employee.dto;

import inflearn.mini.workHub.employee.domain.Employee;
import inflearn.mini.workHub.employee.domain.Role;

import java.time.LocalDate;

public record EmployeeInfoResponse(String name, String teamName, Role role, LocalDate birthday, LocalDate workStartDt) {


    public static EmployeeInfoResponse from(final Employee employee) {
        return new EmployeeInfoResponse(
                employee.getName(),
                employee.getTeam().getName(),
                employee.getRole(),
                employee.getBirthday(),
                employee.getWorkStartDt());
    }
}
