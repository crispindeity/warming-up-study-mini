package me.sungbin.domain.member.model.request;

import me.sungbin.domain.member.entity.Employee;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.model
 * @fileName : FindEmployeesInfoResponseDto
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public record EmployeesInfoResponseDto(String name, String teamName, String role, LocalDate birthDay, LocalDate workStartDate) {

    public EmployeesInfoResponseDto(Employee employee) {
        this(employee.getName(), employee.getTeamName(), employee.getRole(), employee.getBirthday(), employee.getCreatedAt());
    }
}
