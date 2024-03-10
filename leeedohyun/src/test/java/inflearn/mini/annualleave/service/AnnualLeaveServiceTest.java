package inflearn.mini.annualleave.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.exception.InvalidAnnualLeaveRequestException;
import inflearn.mini.annualleave.repository.AnnualLeaveRepository;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;

@ExtendWith(MockitoExtension.class)
class AnnualLeaveServiceTest {

    @Mock
    private AnnualLeaveRepository annualLeaveRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AnnualLeaveService annualLeaveService;

    @Test
    void 연차를_신청한다() {
        // given
        final AnnualLeaveRequestDto annualLeaveRequestDto = new AnnualLeaveRequestDto(1L, LocalDate.now().plusDays(4));

        final Employee employee = Employee.builder()
                .name("홍길동")
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Team team = new Team("개발팀", 3);
        employee.joinTeam(team);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));

        // when
        annualLeaveService.requestAnnualLeave(annualLeaveRequestDto);

        // then
        verify(annualLeaveRepository).save(any());
    }

    @Test
    void 연차_등록_시_등록된_직원이_아니면_예외가_발생한다() {
        // given
        final AnnualLeaveRequestDto request = new AnnualLeaveRequestDto(1L, LocalDate.now().plusDays(5));

        given(employeeRepository.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 없습니다."));

        // when

        // then
        assertThatThrownBy(() -> annualLeaveService.requestAnnualLeave(request))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("등록된 직원이 없습니다.");
    }

    @Test
    void 연차_등록_시_사용일_기준으로_등록_기간이_넘으면_예외가_발생한다() {
        // given
        final AnnualLeaveRequestDto request = new AnnualLeaveRequestDto(1L, LocalDate.now().plusDays(5));
        final Employee employee = Employee.builder()
                .name("홍길동")
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Team team = new Team("개발팀", 7);
        employee.joinTeam(team);

        given(employeeRepository.findById(anyLong()))
                .willReturn(Optional.of(employee));

        // when

        // then
        assertThatThrownBy(() -> annualLeaveService.requestAnnualLeave(request))
                .isInstanceOf(InvalidAnnualLeaveRequestException.class)
                .hasMessage("연차 신청 기간이 아닙니다.");
    }
}
