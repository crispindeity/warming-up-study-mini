package inflearn.mini.workHub.employee.domain;

import inflearn.mini.workHub.team.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@RequiredArgsConstructor(access = PROTECTED)
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private boolean managerYn;
    @ManyToOne
    private Team team;
    private LocalDate workStartDt;
    private LocalDate birthday;

    public Employee(String name, boolean managerYn,LocalDate birthday) {
        this.name = name;
        this.managerYn = managerYn;
        this.workStartDt = LocalDate.now();
        this.birthday = birthday;
    }

    public void joinTeam(Team team) {
        this.team = team;
        team.addEmployee(this);
    }

    public Role getRole() {
       return managerYn ? Role.MANAGER : Role.MEMBER;
    }

}
