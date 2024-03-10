package inflearn.mini.annualleave.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.team.domain.Team;

class AnnualLeaveTest {

    @Test
    void 연차_신청_기간이_아닌_경우_예외가_발생한다() {
        // given
        final Team team = new Team("개발팀", 3);
        final Employee employee = Employee.builder()
                .name("홍길동")
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        employee.joinTeam(team);
        final AnnualLeave annualLeave = new AnnualLeave(LocalDate.of(2024, 3, 14),
                LocalDate.of(2024, 3, 15),
                employee);

        // when

        // then
        assertThatThrownBy(() -> annualLeave.validateLeaveRegistrationAdvanceDays(team))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("연차 신청 기간이 아닙니다.");
    }
}
