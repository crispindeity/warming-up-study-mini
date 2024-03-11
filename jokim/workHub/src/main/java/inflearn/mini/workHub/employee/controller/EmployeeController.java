package inflearn.mini.workHub.employee.controller;

import inflearn.mini.workHub.employee.dto.EmployeeInfoResponse;
import inflearn.mini.workHub.employee.dto.EmployeeRegisterRequest;
import inflearn.mini.workHub.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping("/workhub/employee/register")
    public void registerEmployee(@RequestBody @Valid EmployeeRegisterRequest request){
        employeeService.registerEmployee(request);
    }

    @GetMapping("/workhub/employee/infoList")
    public List<EmployeeInfoResponse> getEmployeeInfoList() {
        return employeeService.getEmployeeInfoList();
    }
}
