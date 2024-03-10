package inflearn.mini.commute.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import inflearn.mini.employee.domain.Employee;

class CommuteTest {

    @Test
    void 퇴근_시간과_출근_시간이_같으면_true() {
        // given
        final Employee employee = Employee.builder()
                .name("테스트")
                .isManager(true)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Commute commute = new Commute(employee);
        final LocalDate workStartDate = LocalDate.of(2021, 1, 1);
        commute.leaveWork(LocalDateTime.of(2021, 1, 1, 9, 0));

        // when
        final boolean workEndDateEqualToWorkStartDate = commute.isWorkEndDateEqualToWorkStartDate(workStartDate);

        // then
        assertThat(workEndDateEqualToWorkStartDate).isTrue();
    }

    @Test
    void 퇴근_시간과_출근_시간이_다르면_false() {
        // given
        final Employee employee = Employee.builder()
                .name("테스트")
                .isManager(true)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Commute commute = new Commute(employee);
        final LocalDate workStartDate = LocalDate.of(2021, 1, 2);
        commute.leaveWork(LocalDateTime.of(2021, 1, 1, 9, 0));

        // when
        final boolean workEndDateEqualToWorkStartDate = commute.isWorkEndDateEqualToWorkStartDate(workStartDate);

        // then
        assertThat(workEndDateEqualToWorkStartDate).isFalse();
    }
}
