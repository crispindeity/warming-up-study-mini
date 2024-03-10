package inflearn.mini.annualleave.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import inflearn.mini.annualleave.exception.InvalidAnnualLeaveRequestException;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.team.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AnnualLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_leave_id")
    private Long id;

    private LocalDate requestDate;

    private LocalDate useDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public AnnualLeave(final LocalDate requestDate, final LocalDate useDate, final Employee employee) {
        this.requestDate = requestDate;
        this.useDate = useDate;
        this.employee = employee;
    }

    public void validateLeaveRegistrationAdvanceDays(final Team team) {
        if (ChronoUnit.DAYS.between(requestDate, useDate) < team.getLeaveRegistrationAdvanceDays()) {
            throw new InvalidAnnualLeaveRequestException("연차 신청 기간이 아닙니다.");
        }
    }
}
