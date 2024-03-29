package inflearn.mini.commute.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import inflearn.mini.employee.domain.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commute_id")
    private Long id;

    private LocalDateTime workStartTime;

    private LocalDateTime workEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public Commute(final Employee employee) {
        this.employee = employee;
    }

    public void goToWork() {
        this.workStartTime = LocalDateTime.now();
    }

    public void goToWork(final LocalDateTime workingStartTime) {
        this.workStartTime = workingStartTime;
    }

    public void leaveWork(final LocalDateTime workingEndTime) {
        this.workEndTime = workingEndTime;
    }

    public boolean isWorkEndDateEqualToWorkStartDate(final LocalDate workingStartDate) {
        return workEndTime.toLocalDate().isEqual(workingStartDate);
    }

    public long calculateWorkingHours() {
        return ChronoUnit.MINUTES.between(workStartTime, workEndTime);
    }

    public LocalDate getWorkStartDate() {
        return workStartTime.toLocalDate();
    }
}
