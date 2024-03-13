package inflearn.mini.team.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import inflearn.mini.employee.domain.Employee;

class TeamTest {

    @Test
    void 매니저_이름을_찾는다() {
        // given
        final Employee manager = Employee.builder()
                .name("홍길동")
                .isManager(true)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Team team = new Team("팀명");
        manager.joinTeam(team);

        // when
        final String managerName = team.getManagerName();

        // then
        assertThat(managerName).isEqualTo("홍길동");
    }

    @Test
    void 매니저가_없으면_null을_반환한다() {
        // given
        final Employee employee = Employee.builder()
                .name("홍길동")
                .isManager(false)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Team team = new Team("팀명");
        employee.joinTeam(team);

        // when
        final String managerName = team.getManagerName();

        // then
        assertThat(managerName).isNull();
    }

    @Test
    void 팀_인원_수를_반환한다() {
        // given
        final Employee employee = Employee.builder()
                .name("홍길동")
                .isManager(false)
                .workStartDate(LocalDate.of(2020, 1, 1))
                .build();
        final Team team = new Team("팀명");
        employee.joinTeam(team);

        // when
        final int countEmployees = team.countEmployees();

        // then
        assertThat(countEmployees).isEqualTo(1);
    }
}
