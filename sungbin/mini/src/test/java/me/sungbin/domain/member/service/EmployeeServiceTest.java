package me.sungbin.domain.member.service;

import me.sungbin.domain.member.entity.Employee;
import me.sungbin.domain.member.model.request.RegisterEmployeeRequestDto;
import me.sungbin.domain.member.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.exception.custom.AlreadyExistsManagerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.service
 * @fileName : EmployeeServiceTest
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@SpringBootTest
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("직원 등록 테스트 - 실패 (이미 존재하는 매니저)")
    void register_employee_test_fail_caused_by_already_exits_manager() {
        // Given
        RegisterEmployeeRequestDto requestDto = new RegisterEmployeeRequestDto("장그래", "영업팀", true, LocalDate.of(1992, 2, 22));
        Team mockTeam = new Team("영업팀");
        mockTeam.addEmployee(new Employee("기존 매니저", true, LocalDate.of(1990, 1, 1))); // 이미 매니저가 존재하는 상태를 모의
        when(teamRepository.findByName("영업팀")).thenReturn(Optional.of(mockTeam));

        // When & Then
        assertThrows(AlreadyExistsManagerException.class, () -> {
            employeeService.registerEmployee(requestDto);
        });
    }
}
