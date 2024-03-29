package me.sungbin.domain.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.domain.employee.model.request.EmployeesInfoResponseDto;
import me.sungbin.domain.employee.model.request.RegistrationEmployeeRequestDto;
import me.sungbin.domain.employee.model.response.OvertimeResponseDto;
import me.sungbin.domain.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.controller
 * @fileName : EmployeeController
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/register")
    public void registerEmployee(@RequestBody @Valid RegistrationEmployeeRequestDto requestDto) {
        this.employeeService.registerEmployee(requestDto);
    }

    @GetMapping
    public List<EmployeesInfoResponseDto> findEmployeesInfo() {
        return this.employeeService.findEmployeesInfo();
    }

    @GetMapping("/overtime")
    public List<OvertimeResponseDto> findOverTimeList(YearMonth date) {
        return this.employeeService.calculateOvertimeHours(date);
    }

    @GetMapping("/test")
    public void test() {
        YearMonth previousMonth = YearMonth.now();
        List<OvertimeResponseDto> overtimeRecords = this.employeeService.calculateOvertimeHours(previousMonth);
        this.employeeService.exportOverTimeRecordsToCSV(overtimeRecords, "overtime_" + previousMonth + ".csv");
    }
}
