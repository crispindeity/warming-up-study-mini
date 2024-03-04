package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.yeonghuns.common.BaseEntity;
import org.example.yeonghuns.common.CreatedDateEntity;
import org.example.yeonghuns.dto.member.response.GetAllMembersResponse;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AttributeOverride(name = "createdAt", column = @Column(name = "work_start_date"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreatedDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean role;

    private LocalDate birthday;

    @ManyToOne
    private Team team;

    @Builder
    public Member(String name, boolean role, LocalDate birthday, Team team) {
        this.name = name;
        this.role = role;
        this.birthday = birthday;
        this.team = team;
    }

    public void changeRole() {
        this.role = !this.role;
    }
}
