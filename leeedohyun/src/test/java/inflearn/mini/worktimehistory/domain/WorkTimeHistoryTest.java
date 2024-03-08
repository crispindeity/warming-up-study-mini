package inflearn.mini.worktimehistory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import inflearn.mini.employee.domain.Employee;

class WorkTimeHistoryTest {

    @Test
    void 퇴근_시간과_출근_시간이_같으면_true() {
        // given
        final Employee employee = Employee.builder()
                .name("테스트")
                .isManager(true)
                .build();
        final WorkTimeHistory workTimeHistory = new WorkTimeHistory(employee);
        final LocalDate workStartDate = LocalDate.of(2021, 1, 1);
        workTimeHistory.leaveWork(LocalDateTime.of(2021, 1, 1, 9, 0));

        // when
        final boolean workEndDateEqualToWorkStartDate = workTimeHistory.isWorkEndDateEqualToWorkStartDate(workStartDate);

        // then
        assertThat(workEndDateEqualToWorkStartDate).isTrue();
    }

    @Test
    void 퇴근_시간과_출근_시간이_다르면_false() {
        // given
        final Employee employee = Employee.builder()
                .name("테스트")
                .isManager(true)
                .build();
        final WorkTimeHistory workTimeHistory = new WorkTimeHistory(employee);
        final LocalDate workStartDate = LocalDate.of(2021, 1, 2);
        workTimeHistory.leaveWork(LocalDateTime.of(2021, 1, 1, 9, 0));

        // when
        final boolean workEndDateEqualToWorkStartDate = workTimeHistory.isWorkEndDateEqualToWorkStartDate(workStartDate);

        // then
        assertThat(workEndDateEqualToWorkStartDate).isFalse();
    }
}
