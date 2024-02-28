package inflearn.mini.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.dto.response.EmployeeResponse;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;
import inflearn.mini.team.exception.TeamNotFoundException;
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

    @Test
    void 직원을_등록할_때_팀이_존재하지_않으면_예외가_발생한다() {
        // given
        final EmployeeRegisterRequestDto request = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        given(teamRepository.findByName(anyString()))
                .willThrow(new TeamNotFoundException("존재하지 않는 팀입니다."));

        // when
        // then
        assertThatThrownBy(() -> employeeService.registerEmployee(request))
                .isInstanceOf(TeamNotFoundException.class)
                .hasMessage("존재하지 않는 팀입니다.");
    }

    @Test
    void 직원을_조회한다() {
        // given
        final Team team = new Team("개발팀");
        final Employee employee1 = Employee.builder()
                .name("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        final Employee employee2 = Employee.builder()
                .name("김철수")
                .isManager(true)
                .birthday(LocalDate.of(1980, 1, 1))
                .workStartDate(LocalDate.of(2018, 1, 1))
                .build();

        employee1.joinTeam(team);
        employee2.joinTeam(team);

        given(employeeRepository.findAll())
                .willReturn(List.of(employee1, employee2));

        // when
        final List<EmployeeResponse> employees = employeeService.getEmployees();

        // then
        assertThat(employees).hasSize(2);
    }
}
