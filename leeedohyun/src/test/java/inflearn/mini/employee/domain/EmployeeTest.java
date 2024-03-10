package inflearn.mini.employee.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import inflearn.mini.annualleave.exception.ExhaustedAnnualLeaveException;

class EmployeeTest {

    @Test
    void isManager가_true인_경우_MANAGER를_반환한다() {
        // given
        final Employee employee = Employee.builder()
                .isManager(true)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();

        // when
        final Role role = employee.getRole();

        // then
        assertThat(role).isEqualTo(Role.MANAGER);
    }

    @Test
    void isManager가_false인_경우_MEMBER를_반환한다() {
        // given
        final Employee employee = Employee.builder()
                .isManager(false)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();

        // when
        final Role role = employee.getRole();

        // then
        assertThat(role).isEqualTo(Role.MEMBER);
    }

    @Test
    void 연차를_모두_사용한_경우_예외가_발생한다() {
        // given
        final Employee employee = Employee.builder()
                .workStartDate(LocalDate.now())
                .build();

        // when
        for (int i = 0; i < 11; i++) {
            employee.useAnnualLeave();
        }

        // then
        assertThatThrownBy(employee::useAnnualLeave)
                .isInstanceOf(ExhaustedAnnualLeaveException.class)
                .hasMessage("연차를 모두 사용하였습니다.");
    }

    @Test
    void 올해_입사한_직원은_연차가_11개_주어진다() {
        // given
        final Employee employee = Employee.builder()
                .workStartDate(LocalDate.now())
                .build();

        // when
        final int annualLeaveNumber = employee.getAnnualLeaveNumber();

        // then
        assertThat(annualLeaveNumber).isEqualTo(11);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10})
    void 그_외_직원은_연차가_15개_주어진다(final int yearsToSubtract) {
        // given
        final Employee employee = Employee.builder()
                .workStartDate(LocalDate.now().minusYears(yearsToSubtract))
                .build();

        // when
        final int annualLeaveNumber = employee.getAnnualLeaveNumber();

        // then
        assertThat(annualLeaveNumber).isEqualTo(15);
    }
}
