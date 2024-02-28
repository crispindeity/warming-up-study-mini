package inflearn.mini.employee.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;
import inflearn.mini.team.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void 직원을_등록한다() {
        // given
        final EmployeeRegisterRequestDto request = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        final Team team = new Team("개발팀");

        given(teamRepository.findByName(anyString()))
                .willReturn(Optional.of(team));

        // when
        employeeService.registerEmployee(request);

        // then
        final Employee employee = request.toEntity();
        employee.joinTeam(team);
        verify(employeeRepository).save(refEq(employee));
    }
}
