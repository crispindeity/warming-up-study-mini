package inflearn.mini.workHub.employee.dto;

import inflearn.mini.workHub.employee.domain.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EmployeeRegisterRequest(
        @NotNull(message = "이름은 null일 수 없습니다.")
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,
        @NotNull(message = "팀명은 null일 수 없습니다.")
        @NotBlank(message = "팀명은 공백일 수 없습니다.")
        String teamName,
        boolean managerYn,
        @NotNull(message = "생년월일은 null일 수 없습니다.")
        @PastOrPresent(message = "생년월일은 미래일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday) {

    public  Employee toEntity(){
        return new Employee(name, managerYn, birthday);
    }
}
