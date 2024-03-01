package me.sungbin.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.member.entity.Employee;
import me.sungbin.domain.member.model.request.RegisterEmployeeRequestDto;
import me.sungbin.domain.member.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.exception.custom.TeamNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registerEmployee(RegisterEmployeeRequestDto requestDto) {
        Employee employee = requestDto.toEntity();
        this.employeeRepository.save(employee);

        Team team = this.teamRepository.findByName(requestDto.teamName()).orElseThrow(TeamNotFoundException::new);
        team.addEmployee(employee);

        this.teamRepository.save(team);
    }
}
