package inflearn.mini.worktimehistory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;
import inflearn.mini.worktimehistory.domain.WorkTimeHistory;
import inflearn.mini.worktimehistory.repsoitory.WorkTimeHistoryRepository;

@ExtendWith(MockitoExtension.class)
class WorkTimeHistoryServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private WorkTimeHistoryRepository workTimeHistoryRepository;

    @InjectMocks
    private WorkTimeHistoryService workTimeHistoryService;

    @Test
    void 출근한다() {
        // given
        final Employee employee = Employee.builder()
                .id(1L)
                .name("홍길동")
                .isManager(false)
                .build();
        employee.joinTeam(new Team("개발팀"));
        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));

        given(workTimeHistoryRepository.existsByEmployeeAndWorkEndTimeIsNull(any()))
                .willReturn(false);

        // when
        workTimeHistoryService.goToWork(1L);

        // then
        verify(workTimeHistoryRepository).save(any(WorkTimeHistory.class));
    }

    @Test
    void 출근_시_직원_등록이_안_되어_있으면_예외가_발생한다() {
        // given
        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> workTimeHistoryService.goToWork(1L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("등록된 직원이 없습니다.");
    }

    @Test
    void 출근_시_이미_출근한_경우_예외가_발생한다() {
        // given
        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(Employee.builder().build()));

        given(workTimeHistoryRepository.existsByEmployeeAndWorkEndTimeIsNull(any()))
                .willReturn(true);

        // when

        // then
        assertThatThrownBy(() -> workTimeHistoryService.goToWork(1L))
                .isInstanceOf(AlreadyAtWorkException.class)
                .hasMessage("이미 출근한 직원입니다.");
    }

    @Test
    void 퇴근한다() {
        // given
        final Employee employee = Employee.builder()
                .id(1L)
                .name("홍길동")
                .isManager(false)
                .build();
        employee.joinTeam(new Team("개발팀"));
        final WorkTimeHistory workTimeHistory = new WorkTimeHistory(employee);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));
        given(workTimeHistoryRepository.findByEmployeeAndWorkStartTimeAndAndWorkEndTimeIsNull(any(), any(), any()))
                .willReturn(Optional.of(workTimeHistory));

        // when
        workTimeHistoryService.leaveWork(1L);

        // then
        assertThat(workTimeHistory.getWorkEndTime()).isNotNull();
    }

    @Test
    void 퇴근_시_등록되지_않은_직원인_경우_예외가_발생한다() {
        // given
        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> workTimeHistoryService.leaveWork(1L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("등록된 직원이 없습니다.");
    }

    @Test
    void 퇴근_시_출근하지_않은_직원인_경우_예외가_발생한다() {
        // given
        final Employee employee = Employee.builder()
                .id(1L)
                .name("홍길동")
                .isManager(false)
                .build();
        employee.joinTeam(new Team("개발팀"));

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));
        given(workTimeHistoryRepository.findByEmployeeAndWorkStartTimeAndAndWorkEndTimeIsNull(any(), any(), any()))
                .willThrow(new AbsentEmployeeException("출근하지 않은 직원입니다."));

        // when

        // then
        assertThatThrownBy(() -> workTimeHistoryService.leaveWork(1L))
                .isInstanceOf(AbsentEmployeeException.class)
                .hasMessage("출근하지 않은 직원입니다.");
    }
}
