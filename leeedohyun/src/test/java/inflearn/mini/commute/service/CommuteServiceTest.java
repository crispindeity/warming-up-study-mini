package inflearn.mini.commute.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.annualleave.repository.AnnualLeaveRepository;
import inflearn.mini.commute.domain.Commute;
import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import inflearn.mini.commute.repsoitory.CommuteRepository;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.dto.request.EmployeeDailyWorkingHoursRequest;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;

@ExtendWith(MockitoExtension.class)
class CommuteServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CommuteRepository commuteRepository;

    @Mock
    private AnnualLeaveRepository annualLeaveRepository;

    @InjectMocks
    private CommuteService commuteService;

    @Test
    void 출근한다() {
        // given
        final Employee employee = Employee.builder()
                .id(1L)
                .name("홍길동")
                .isManager(false)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));

        final CommutingRequestDto commutingRequest = new CommutingRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));

        given(commuteRepository.existsByEmployeeAndWorkEndTimeIsNull(any()))
                .willReturn(false);

        // when
        commuteService.goToWork(commutingRequest);

        // then
        verify(commuteRepository).save(any(Commute.class));
    }

    @Test
    void 출근_시_직원_등록이_안_되어_있으면_예외가_발생한다() {
        // given
        final CommutingRequestDto commutingRequest = new CommutingRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> commuteService.goToWork(commutingRequest))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("등록된 직원이 없습니다.");
    }

    @Test
    void 출근_시_이미_출근한_경우_예외가_발생한다() {
        // given
        final CommutingRequestDto commutingRequest = new CommutingRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(Employee.builder()
                        .workStartDate(LocalDate.of(2020, 1, 1))
                        .build()));

        given(commuteRepository.existsByEmployeeAndWorkEndTimeIsNull(any()))
                .willReturn(true);

        // when

        // then
        assertThatThrownBy(() -> commuteService.goToWork(commutingRequest))
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
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));
        final Commute commute = new Commute(employee);
        final EndOfWorkRequestDto endOfWorkRequest = new EndOfWorkRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));
        given(commuteRepository.findCommuteForDate(any(), any(), any()))
                .willReturn(Optional.of(commute));

        // when
        commuteService.leaveWork(endOfWorkRequest);

        // then
        assertThat(commute.getWorkEndTime()).isNotNull();
    }

    @Test
    void 퇴근_시_등록되지_않은_직원인_경우_예외가_발생한다() {
        // given
        final EndOfWorkRequestDto endOfWorkRequest = new EndOfWorkRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> commuteService.leaveWork(endOfWorkRequest))
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
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));
        final EndOfWorkRequestDto endOfWorkRequest = new EndOfWorkRequestDto(1L);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));
        given(commuteRepository.findCommuteForDate(any(), any(), any()))
                .willThrow(new AbsentEmployeeException("출근하지 않은 직원입니다."));

        // when

        // then
        assertThatThrownBy(() -> commuteService.leaveWork(endOfWorkRequest))
                .isInstanceOf(AbsentEmployeeException.class)
                .hasMessage("출근하지 않은 직원입니다.");
    }

    @Test
    void 특정_직원의_날짜별_근무_시간을_조회_시_등록되지_않은_직원인_경우_예외가_발생한다() {
        // given
        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> commuteService.getEmployeeDailyWorkingHours(1L, new EmployeeDailyWorkingHoursRequest(YearMonth.of(2024, 3))))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("등록된 직원이 없습니다.");
    }
}
