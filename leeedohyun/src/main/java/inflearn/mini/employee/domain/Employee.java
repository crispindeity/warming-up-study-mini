package inflearn.mini.employee.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import inflearn.mini.annualleave.exception.ExhaustedAnnualLeaveException;
import inflearn.mini.team.domain.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Employee {

    private static final int ANNUAL_LEAVE_NEW_EMPLOYEE = 11;
    private static final int ANNUAL_LEAVE_REGULAR_EMPLOYEE = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name")
    private String name;

    private boolean isManager;

    private LocalDate workStartDate;

    private LocalDate birthday;

    private int annualLeaveNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Builder
    public Employee(final Long id, final String name, final boolean isManager, final LocalDate workStartDate,
                    final LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.isManager = isManager;
        this.workStartDate = workStartDate;
        this.birthday = birthday;
        this.annualLeaveNumber = giveAnnualLeave();
    }

    public void joinTeam(final Team team) {
        this.team = team;
        team.addEmployee(this);
    }

    public Role getRole() {
        if (isManager) {
            return Role.MANAGER;
        }
        return Role.MEMBER;
    }

    public void useAnnualLeave() {
        validateAnnualLeave();
        annualLeaveNumber--;
    }

    private void validateAnnualLeave() {
        if (annualLeaveNumber <= 0) {
            throw new ExhaustedAnnualLeaveException("연차를 모두 사용하였습니다.");
        }
    }

    private int giveAnnualLeave() {
        if (workStartDate.getYear() == LocalDate.now().getYear()) {
            return ANNUAL_LEAVE_NEW_EMPLOYEE;
        }
        return ANNUAL_LEAVE_REGULAR_EMPLOYEE;
    }
}
