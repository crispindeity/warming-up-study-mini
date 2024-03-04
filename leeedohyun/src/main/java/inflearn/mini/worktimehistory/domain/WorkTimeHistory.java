package inflearn.mini.worktimehistory.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class WorkTimeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_time_history_id")
    private Long id;

    private LocalDateTime workStartTime;

    private LocalDateTime workEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public WorkTimeHistory(final Employee employee) {
        this.employee = employee;
    }

    public void goToWork() {
        this.workStartTime = LocalDateTime.now();
    }

    public void leaveWork(final LocalDateTime workingEndTime) {
        this.workEndTime = workingEndTime;
    }

    public boolean isWorkEndDateEqualToWorkStartDate(final LocalDate workingStartDate) {
        return workEndTime.toLocalDate().isEqual(workingStartDate);
    }
}
