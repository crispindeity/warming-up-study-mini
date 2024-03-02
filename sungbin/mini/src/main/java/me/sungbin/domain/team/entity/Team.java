package me.sungbin.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.global.common.entity.BaseDateTimeEntity;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.entity
 * @fileName : Team
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class Team extends BaseDateTimeEntity {

    @Id
    @Comment("팀 테이블 PK")
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("팀 이름")
    @Column(name = "team_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Employee> employees = new ArrayList<>();

    @Builder
    public Team(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.updateTeam(this);
    }

    public String getManagerName() {
        return employees.stream()
                .filter(Employee::isManager)
                .map(Employee::getName)
                .findFirst()
                .orElse(null);
    }

    public boolean hasManager() {
        return this.employees.stream().anyMatch(Employee::isManager);
    }

    public int getEmployeeCount() {
        return employees != null ? employees.size() : 0;
    }
}
