package me.sungbin.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.member.entity.Employee;
import me.sungbin.domain.member.model.request.EmployeesInfoResponseDto;
import me.sungbin.domain.member.model.request.RegistrationEmployeeRequestDto;
import me.sungbin.domain.member.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.exception.custom.AlreadyExistsManagerException;
import me.sungbin.global.exception.custom.TeamNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.service
 * @fileName : EmployeeService
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TeamRepository teamRepository;

    @Transactional
    public void registerEmployee(RegistrationEmployeeRequestDto requestDto) {
        Employee employee = requestDto.toEntity();

        Team team = this.teamRepository.findByName(requestDto.teamName()).orElseThrow(TeamNotFoundException::new);

        // 매니저가 이미 존재하는 경우 예외 발생
        if (employee.isManager() && team.hasManager()) {
            throw new AlreadyExistsManagerException("이미 매니저가 해당 팀에 존재합니다.");
        }

        this.employeeRepository.save(employee);
        team.addEmployee(employee);
        this.teamRepository.save(team);
    }

    public List<EmployeesInfoResponseDto> findEmployeesInfo() {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees.stream().map(EmployeesInfoResponseDto::new).toList();
    }
}
