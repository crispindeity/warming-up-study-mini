package inflearn.mini.employee.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name")
    private String name;

    private boolean isManager;

    private LocalDate workStartDate;

    private LocalDate birthday;

    public Employee(final String name, final boolean isManager, final LocalDate workStartDate,
                    final LocalDate birthday) {
        this.name = name;
        this.isManager = isManager;
        this.workStartDate = workStartDate;
        this.birthday = birthday;
    }
}
