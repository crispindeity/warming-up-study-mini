package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
@Getter
@Entity
@DynamicInsert
public class Member {

    protected Member() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isManager;

    @Column(nullable = false)
    private LocalDate joinDate;
    @Column(nullable = false)
    private LocalDate birthday;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Team team;

    @Builder
    public Member(long id, String name, boolean isManager, LocalDate joinDate, LocalDate birthday, Team team) {
        this.id = id;
        this.name = name;
        this.isManager = isManager;
        this.joinDate = joinDate;
        this.birthday = birthday;
        this.team = team;
    }

    public void doManager(){
        this.isManager = true;
    }

    public void doStaff(){
        this.isManager=false;
    }

}
