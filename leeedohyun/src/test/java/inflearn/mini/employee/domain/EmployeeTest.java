package inflearn.mini.employee.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void isManager가_true인_경우_MANAGER를_반환한다() {
        // given
        final Employee employee = Employee.builder()
                .isManager(true)
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
                .build();

        // when
        final Role role = employee.getRole();

        // then
        assertThat(role).isEqualTo(Role.MEMBER);
    }
}
