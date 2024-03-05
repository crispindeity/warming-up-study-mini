package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    @OneToMany(mappedBy = "member")
    private List<Commute> commutes;


    @Builder
    public Member(String name, boolean isManager, LocalDate joinDate, LocalDate birthday, Team team) {
        this.name = name;
        this.isManager = isManager;
        this.joinDate = joinDate;
        this.birthday = birthday;
        this.team = team;
    }

    public void changeRole(){
        this.isManager = !this.isManager;
    }

}
