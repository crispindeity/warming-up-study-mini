package me.sungbin.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.domain.member.model.FindEmployeesInfoResponseDto;
import me.sungbin.domain.member.model.request.RegisterEmployeeRequestDto;
import me.sungbin.domain.member.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

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
    public void registerEmployee(@RequestBody @Valid RegisterEmployeeRequestDto requestDto) {
        this.employeeService.registerEmployee(requestDto);
    }

    @GetMapping
    public List<FindEmployeesInfoResponseDto> findEmployeesInfo() {
        return this.employeeService.findEmployeesInfo();
    }
}
