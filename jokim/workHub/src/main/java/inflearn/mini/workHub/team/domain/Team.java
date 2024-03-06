package inflearn.mini.workHub.team.domain;

import inflearn.mini.workHub.employee.domain.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@RequiredArgsConstructor(access = PROTECTED)
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private final List<Employee> employees = new ArrayList<>();


    @Builder
    public Team(final String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public boolean existManager() {
        return employees.stream().anyMatch(Employee::isManagerYn);
    }

    public String getManagerName() {
        return employees.stream()
                .filter(Employee::isManagerYn)
                .map(Employee::getName)
                .findFirst()
                .orElse(null);
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}
