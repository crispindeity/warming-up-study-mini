package inflearn.mini.employee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.dto.request.EmployeeWorkHistoryRequest;
import inflearn.mini.employee.dto.response.EmployeeResponse;
import inflearn.mini.employee.dto.response.EmployeeCommuteResponse;
import inflearn.mini.employee.service.EmployeeService;
import inflearn.mini.commute.service.CommuteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CommuteService commuteService;

    @PostMapping("/register")
    public void registerEmployee(@RequestBody EmployeeRegisterRequestDto request) {
        employeeService.registerEmployee(request);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeCommuteResponse> getEmployeeDailyWorkingHours(@PathVariable final Long employeeId,
                                                                                @RequestBody final EmployeeWorkHistoryRequest request) {
        return ResponseEntity.ok(commuteService.getEmployeeDailyWorkingHours(employeeId, request));
    }
}
