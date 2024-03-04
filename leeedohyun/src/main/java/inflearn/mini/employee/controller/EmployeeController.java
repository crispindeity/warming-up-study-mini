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
import inflearn.mini.employee.dto.response.EmployeeResponse;
import inflearn.mini.employee.service.EmployeeService;
import inflearn.mini.worktimehistory.service.WorkTimeHistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final WorkTimeHistoryService workTimeHistoryService;

    @PostMapping("/register")
    public void registerEmployee(@RequestBody EmployeeRegisterRequestDto request) {
        employeeService.registerEmployee(request);
    }

    @PostMapping("/{employeeId}/work")
    public void goToWork(@PathVariable final Long employeeId) {
        workTimeHistoryService.goToWork(employeeId);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }
}
