package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.yeonghuns.dto.member.response.GetAllMembersResponse;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

    protected Member() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean role;

    private LocalDate birthday;

    @CreatedDate
    private LocalDateTime workStartDate;

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
